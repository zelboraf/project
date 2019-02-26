package com.example.project.offer;

import lombok.Data;

@Data
public class Counter {

	private long all;
	private int processed;
	private int created;
	private int updated;

	public Counter() {
		this.processed = 0;
		this.created = 0;
		this.updated = 0;
	}

	public void incProcessed() { this.processed++; }
	public void incUpdated() { this.updated++; }
	public void incCreated() { this.created++; }

}
