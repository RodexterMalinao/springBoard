package com.bomwebportal.web.ext;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;

import com.google.gson.Gson;

public class JsonView implements View {
	private Object model;
	private Gson gson;

	public JsonView(Object model) {
		this.model = model;
		gson = new Gson();
	}

	public void render(Map model, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String json = gson.toJson(this.model);
		response.getOutputStream().write(json.getBytes());
	}

	public static ModelAndView modelAndView(Object model) {
		return new ModelAndView(new JsonView(model));
	}

	public String getContentType() {
		return "application/json";
	}
}