package it.omicron.esercizio.Model;

public class ResourceNode {

	private Integer id; // 12244,
	private String type; // image,
	private String version; // 1.0.0
	
	public ResourceNode() {
        super();
    }

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
}
