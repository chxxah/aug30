package com.poseidon.web.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.poseidon.web.service.MultiBoardService;

@Controller
public class MultiBoardController {
	
	@Autowired
	private MultiBoardService mbService;

	@GetMapping("/multiboard")
	public String multiboard(
			@RequestParam(value = "board", required = false, defaultValue = "1") int board, 
																					Model model) {
		
		//화면에 보여줄 게시판 목록 가져오기
		List<Map<String, Object>> boardlist = mbService.boardlist();
		List<Map<String, Object>> list = mbService.list(board);
		model.addAttribute("list", list);
		model.addAttribute("boardlist", boardlist);
		//System.out.println(list);
		//System.out.println(boardlist);
//[{mb_cate=1, b_no=1, b_url=../multiboard?board=1, b_comment=게시글 관리, b_catename=메인게시판}, {mb_cate=2, b_no=2, b_url=../multiboard?board=2, b_comment=자유게시판, b_catename=자유게시판}, {mb_cate=3, b_no=3, b_url=../multiboard?board=3, b_comment=공지사항, b_catename=공지사항}, {mb_cate=4, b_no=4, b_url=../multiboard?board=4, b_comment=문의사항, b_catename=문의사항}]
		return "multiboard";
	}
	
	@GetMapping("/mbwrite")
	public String mbWrite(
				@RequestParam(value = "board", required = false, defaultValue = "1") int board,
															Model model, HttpSession session) {
		//로그인한 사용자만 접근할 수 있게 해주세요
		if(session.getAttribute("mid") != null) {
			model.addAttribute("board", board);
			return "mbwrite";
		}else {
			return "redirect:/login.sik?error=login";
		}
	}
	
	@PostMapping("/mbwrite")
	public String mbWrite(@RequestParam Map<String, Object> map, HttpSession session) {
		//로그인 했어?
		if(session.getAttribute("mid") != null) {
			map.put("mid", session.getAttribute("mid"));
			mbService.mbWrite(map);//이번에는 selectKey라는 기법입니다.
			return "redirect:/mbdetail?board" + map.get("board")+ "&mbno="+map.get("mb_no");
		} else {
			return "redirect:/login.sik?error=login";
		}
	}
	
	///mbdetail?mbno="+map.get("mb_no");
	@GetMapping("/mbdetail")
	public String mbDetail(@RequestParam(value = "mbno", required = true) int mbno, Model model) {
		System.out.println(mbno);
		Map<String, Object> detail = mbService.mbdetail(mbno);
		model.addAttribute("detail", detail);
		return "mbdetail";
	}
	
	
	
	
	
	
	
}
