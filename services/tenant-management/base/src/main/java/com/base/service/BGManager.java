package com.base.service;

import java.util.LinkedList;
import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class BGManager {
	
	private List<AbstractBGWork> _hooks;
	
	protected BGManager() {
		_hooks = new LinkedList<AbstractBGWork>();
	}
	
	public void addHook(AbstractBGWork bgWork) {
		_hooks.add(bgWork);
	}

}
