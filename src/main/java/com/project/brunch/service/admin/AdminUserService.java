package com.project.brunch.service.admin;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.brunch.domain.admin.AdminMapper;
import com.project.brunch.domain.post.Post;
import com.project.brunch.domain.post.PostRepository;
import com.project.brunch.domain.user.User;
import com.project.brunch.domain.user.UserRepository;
import com.project.brunch.web.dto.admin.AdminDto;
import com.project.brunch.web.dto.admin.AdminSearchDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class AdminUserService {

	private String TAG = "< AdminUserService >";
	private final UserRepository userRepository;
	private final PostRepository postRepository;
	private final AdminMapper adminMapper;
	
	private final int PAGE_POST_COUNT = 8; // 한 페이지에 존재하는 게시글 수

	@Transactional
	public List<AdminSearchDto> 유저불러오기(int pageNum) {
		Page<User> page = userRepository.findAll(PageRequest.of(pageNum - 1, PAGE_POST_COUNT, Sort.by(Sort.Direction.DESC, "id")));
		List<User> usersEntity = page.getContent();
		List<AdminSearchDto> adminSearchDto = new ArrayList<>();
		
		for (User userEntity : usersEntity) {
			adminSearchDto
			.add(AdminSearchDto.builder()
					.id(userEntity.getId())
					.nickName(userEntity.getNickName())
					.email(userEntity.getEmail())
					.userRole(userEntity.getUserRole().getValue())
					.build());
		}
		
		return adminSearchDto;
	}
		
	// By_아령
	@Transactional
	public String 이메일찾기(int id) {
		User user = adminMapper.findByDelUserEmail(id);
		user.getEmail();
		System.out.println(TAG + "이메일찾기 : " + user);
		return user.getEmail();
	}

	@Transactional
	public void 삭제하기(int id) {
		User user = userRepository.findById(id).get();
		System.out.println(TAG + "삭제하기 : " + id);
		userRepository.delete(user);
	}

	@Transactional
	public AdminDto 회원Count() {
		int allUserCount;
		int allPostCount;
		int todayPostCount = 0;

		// 전체 유저 수
		List<User> userEntity = userRepository.findAll();
		allUserCount = userEntity.size();

		// 전체 글 수
		List<Post> postEntity = postRepository.findAll();
		allPostCount = postEntity.size();

		// 오늘 올라온 글 수
		try {
			List<Post> todayPostEntity = adminMapper.findByTodayPost();
			todayPostCount = todayPostEntity.size();

		} catch (Exception e) {
			e.printStackTrace();
		}

		AdminDto adminDto = AdminDto.builder().allUserCount(allUserCount).allPostCount(allPostCount)
				.todayPostCount(todayPostCount).build();

		return adminDto;
	}

	@Transactional
	public List<AdminSearchDto> 유저검색하기(String keyword) {
		// 1. 검색 키워드 들어간 유저 찾아오기
		List<User> usersEntity = userRepository.findByNickNameContaining(keyword);

		List<AdminSearchDto> adminSearchDto = new ArrayList<>();
		
		if (usersEntity.isEmpty()) {
			return adminSearchDto;
		}
		
		for (User userEntity : usersEntity) {
			adminSearchDto
				.add(AdminSearchDto.builder()
						.id(userEntity.getId())
						.snsId(userEntity.getSnsId())
						.nickName(userEntity.getNickName())
						.email(userEntity.getEmail())
						.userRole(userEntity.getUserRole().getValue())
						.keyword(keyword)
						.build());
		}
		return adminSearchDto;
	}

}
