//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\User\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package org.yaml.snakeyaml.serializer;

import org.yaml.snakeyaml.emitter.*;
import org.yaml.snakeyaml.resolver.*;
import org.yaml.snakeyaml.*;
import org.yaml.snakeyaml.error.*;
import java.io.*;
import java.util.*;
import org.yaml.snakeyaml.nodes.*;
import org.yaml.snakeyaml.events.*;

public final class Serializer
{
    private final Emitable emitter;
    private final Resolver resolver;
    private boolean explicitStart;
    private boolean explicitEnd;
    private DumperOptions.Version useVersion;
    private Map<String, String> useTags;
    private Set<Node> serializedNodes;
    private Map<Node, String> anchors;
    private AnchorGenerator anchorGenerator;
    private Boolean closed;
    private Tag explicitRoot;
    
    public Serializer(final Emitable emitter, final Resolver resolver, final DumperOptions opts, final Tag rootTag) {
        this.emitter = emitter;
        this.resolver = resolver;
        this.explicitStart = opts.isExplicitStart();
        this.explicitEnd = opts.isExplicitEnd();
        if (opts.getVersion() != null) {
            this.useVersion = opts.getVersion();
        }
        this.useTags = (Map<String, String>)opts.getTags();
        this.serializedNodes = new HashSet<Node>();
        this.anchors = new HashMap<Node, String>();
        this.anchorGenerator = opts.getAnchorGenerator();
        this.closed = null;
        this.explicitRoot = rootTag;
    }
    
    public void open() throws IOException {
        if (this.closed == null) {
            this.emitter.emit((Event)new StreamStartEvent((Mark)null, (Mark)null));
            this.closed = Boolean.FALSE;
            return;
        }
        if (Boolean.TRUE.equals(this.closed)) {
            throw new SerializerException("serializer is closed");
        }
        throw new SerializerException("serializer is already opened");
    }
    
    public void close() throws IOException {
        if (this.closed == null) {
            throw new SerializerException("serializer is not opened");
        }
        if (!Boolean.TRUE.equals(this.closed)) {
            this.emitter.emit((Event)new StreamEndEvent((Mark)null, (Mark)null));
            this.closed = Boolean.TRUE;
        }
    }
    
    public void serialize(final Node node) throws IOException {
        if (this.closed == null) {
            throw new SerializerException("serializer is not opened");
        }
        if (this.closed) {
            throw new SerializerException("serializer is closed");
        }
        this.emitter.emit((Event)new DocumentStartEvent((Mark)null, (Mark)null, this.explicitStart, this.useVersion, (Map)this.useTags));
        this.anchorNode(node);
        if (this.explicitRoot != null) {
            node.setTag(this.explicitRoot);
        }
        this.serializeNode(node, null);
        this.emitter.emit((Event)new DocumentEndEvent((Mark)null, (Mark)null, this.explicitEnd));
        this.serializedNodes.clear();
        this.anchors.clear();
    }
    
    private void anchorNode(Node node) {
        if (node.getNodeId() == NodeId.anchor) {
            node = ((AnchorNode)node).getRealNode();
        }
        if (this.anchors.containsKey(node)) {
            String anchor = this.anchors.get(node);
            if (null == anchor) {
                anchor = this.anchorGenerator.nextAnchor(node);
                this.anchors.put(node, anchor);
            }
        }
        else {
            this.anchors.put(node, null);
            switch (node.getNodeId()) {
                case sequence: {
                    final SequenceNode seqNode = (SequenceNode)node;
                    final List<Node> list = (List<Node>)seqNode.getValue();
                    for (final Node item : list) {
                        this.anchorNode(item);
                    }
                    break;
                }
                case mapping: {
                    final MappingNode mnode = (MappingNode)node;
                    final List<NodeTuple> map = (List<NodeTuple>)mnode.getValue();
                    for (final NodeTuple object : map) {
                        final Node key = object.getKeyNode();
                        final Node value = object.getValueNode();
                        this.anchorNode(key);
                        this.anchorNode(value);
                    }
                    break;
                }
            }
        }
    }
    
    private void serializeNode(Node node, final Node parent) throws IOException {
        if (node.getNodeId() == NodeId.anchor) {
            node = ((AnchorNode)node).getRealNode();
        }
        final String tAlias = this.anchors.get(node);
        if (this.serializedNodes.contains(node)) {
            this.emitter.emit((Event)new AliasEvent(tAlias, (Mark)null, (Mark)null));
        }
        else {
            this.serializedNodes.add(node);
            switch (node.getNodeId()) {
                case scalar: {
                    final ScalarNode scalarNode = (ScalarNode)node;
                    final Tag detectedTag = this.resolver.resolve(NodeId.scalar, scalarNode.getValue(), true);
                    final Tag defaultTag = this.resolver.resolve(NodeId.scalar, scalarNode.getValue(), false);
                    final ImplicitTuple tuple = new ImplicitTuple(node.getTag().equals((Object)detectedTag), node.getTag().equals((Object)defaultTag));
                    final ScalarEvent event = new ScalarEvent(tAlias, node.getTag().getValue(), tuple, scalarNode.getValue(), (Mark)null, (Mark)null, scalarNode.getStyle());
                    this.emitter.emit((Event)event);
                    break;
                }
                case sequence: {
                    final SequenceNode seqNode = (SequenceNode)node;
                    final boolean implicitS = node.getTag().equals((Object)this.resolver.resolve(NodeId.sequence, (String)null, true));
                    this.emitter.emit((Event)new SequenceStartEvent(tAlias, node.getTag().getValue(), implicitS, (Mark)null, (Mark)null, seqNode.getFlowStyle()));
                    final List<Node> list = (List<Node>)seqNode.getValue();
                    for (final Node item : list) {
                        this.serializeNode(item, node);
                    }
                    this.emitter.emit((Event)new SequenceEndEvent((Mark)null, (Mark)null));
                    break;
                }
                default: {
                    final Tag implicitTag = this.resolver.resolve(NodeId.mapping, (String)null, true);
                    final boolean implicitM = node.getTag().equals((Object)implicitTag);
                    this.emitter.emit((Event)new MappingStartEvent(tAlias, node.getTag().getValue(), implicitM, (Mark)null, (Mark)null, ((CollectionNode)node).getFlowStyle()));
                    final MappingNode mnode = (MappingNode)node;
                    final List<NodeTuple> map = (List<NodeTuple>)mnode.getValue();
                    for (final NodeTuple row : map) {
                        final Node key = row.getKeyNode();
                        final Node value = row.getValueNode();
                        this.serializeNode(key, (Node)mnode);
                        this.serializeNode(value, (Node)mnode);
                    }
                    this.emitter.emit((Event)new MappingEndEvent((Mark)null, (Mark)null));
                    break;
                }
            }
        }
    }
}
