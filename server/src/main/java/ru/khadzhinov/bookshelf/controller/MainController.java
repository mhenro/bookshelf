package ru.khadzhinov.bookshelf.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainController {	
	private class TmpData {
		private String name;
		private long id;
		
		public TmpData(String name, long id) {
			this.name = name;
			this.id = id;
		}
		
		public String getName() {
			return name;
		}
		
		public long getId() {
			return id;
		}
	}
	
	@RequestMapping("/")
	public TmpData index() {
		TmpData tmpData = new TmpData("Test", 777);
		return tmpData;
	}
}
