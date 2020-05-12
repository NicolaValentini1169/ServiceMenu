package it.omicron.esercizio.Model;

import java.util.List;

public class MenuNode {

	private Integer nodeId; // 0,
	private String nodeName; // RICARICHE,
	private String nodeType; // group,
	private String groupType; // menuTitle,
	private String flowType; // : "PRIMARY",
	private String status; // ACTIVE,
	private long startValidityTs; // 1527066836000,
	private long endValidityTs; // 1727066836000,
	private List<MenuNode> nodes;
	private String tag; // : "ILIAD",
	private ResourceNode resource;

	public MenuNode() {
		super();
	}

	public Integer getNodeId() {
		return nodeId;
	}

	public void setNodeId(Integer nodeId) {
		this.nodeId = nodeId;
	}

	public String getNodeName() {
		return nodeName;
	}

	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}

	public String getNodeType() {
		return nodeType;
	}

	public void setNodeType(String nodeType) {
		this.nodeType = nodeType;
	}

	public String getGroupType() {
		if (groupType == null) return "";
		return groupType;
	}

	public void setGroupType(String groupType) {
		this.groupType = groupType;
	}

	public String getFlowType() {
		if (flowType == null) return "";
		return flowType;
	}

	public void setFlowType(String flowType) {
		this.flowType = flowType;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public long getStartValidityTs() {
		return startValidityTs;
	}

	public void setStartValidityTs(long startValidityTs) {
		this.startValidityTs = startValidityTs;
	}

	public long getEndValidityTs() {
		return endValidityTs;
	}

	public void setEndValidityTs(long endValidityTs) {
		this.endValidityTs = endValidityTs;
	}

	public List<MenuNode> getNodes() {
		return nodes;
	}

	public void setNodes(List<MenuNode> nodes) {
		this.nodes = nodes;
	}

	public String getTag() {
		if (tag == null) return "";
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public ResourceNode getResource() {
		return resource;
	}

	public void setResource(ResourceNode resource) {
		this.resource = resource;
	}
}
