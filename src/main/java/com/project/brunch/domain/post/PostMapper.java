package com.project.brunch.domain.post;

import java.util.List;

//mapper - post.xml
public interface PostMapper {

	// By_민경
	public List<Post> findAllPost();

	// By_아령
	public void updateByPost(int id);
	
}
