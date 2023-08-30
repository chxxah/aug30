package com.poseidon.web.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.commons.mail.EmailException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.poseidon.web.service.AdminService;
import com.poseidon.web.util.Util;

@Controller
@RequestMapping("/admin")
public class AdminController {
	// AdminService / AdminDAO / adminMapper

	@Autowired
	private AdminService adminService;

	@Autowired
	private Util util;

	@GetMapping("/")
	public String adminIndex2() {
		return "forward:/admin/admin";// url경로명을 유지하고 화면내용만 갱신합니다.
	}

	@GetMapping("/admin")
	public String adminIndex() {
		return "admin/index";
	}

	@PostMapping("/login")
	public String adminLogin(@RequestParam Map<String, Object> map, HttpSession session) {
		System.out.println(map);
		Map<String, Object> result = adminService.adminLogin(map);
		System.out.println(result);
		// {m_grade=5, m_name=뽀로로, count=1}
		// System.out.println(String.valueOf(result.get("count")).equals("1"));
		// System.out.println(Integer.parseInt(String.valueOf(result.get("m_grade"))) >
		// 5);

		if (util.obj2Int(result.get("count")) == 1 && util.obj2Int(result.get("m_grade")) > 5) {
			// System.out.println("좋았어! 진행시켜!");
			// 세션 올리기
			session.setAttribute("mid", map.get("id"));
			session.setAttribute("mname", result.get("m_name"));
			session.setAttribute("mgrade", result.get("m_grade"));
			// 메인으로 이동하기
			return "redirect:/admin/main";
		} else {
			return "redirect:/admin/admin?error=login";
		}
	}

	@GetMapping("/main")
	public String main() {
		return "admin/main";// 폴더 적어줘야 admin/밑에 main.jsp를 열어줍니다.
	}

	@GetMapping("/notice")
	public String notice(Model model) {
		// 1 데이터베이스까지 연결하기
		// 2 데이터 불러오기
		List<Map<String, Object>> list = adminService.list();
		// 3 데이터 jsp로 보내기
		model.addAttribute("list", list);
		return "admin/notice";
	}

	@PostMapping("/noticeWrite")
	public String noticeWrite(@RequestParam("upFile") MultipartFile upfile, @RequestParam Map<String, Object> map) {
		// {title=wafwaf, content=awfeawefawefawef, upFile=backup.sql}
		// {title=gregerg, content=vfdsvb bb gr, upFile=}
		// System.out.println(map);

		// 2023-08-22 요구사항확인
		//
		if (!upfile.isEmpty()) {
			// 저장할 경로명 뽑기 request뽑기
			HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
					.getRequest();
			String path = request.getServletContext().getRealPath("/upload");
			// System.out.println("실제 경로 : " + path);

			// upfile정보보기
			// System.out.println(upfile.getOriginalFilename());
			// System.out.println(upfile.getSize());
			// System.out.println(upfile.getContentType());
			// 진짜로 파일 업로드 하기 경로 + 저장할 파일명
			// C:\eGovFrameDev-4.1.0-64bit\workspace\aug09\src\main\webapp\
			// upload\20171109_5a03383e9c5c4.gif
			// String타입의 경로를 file형태로 바꿔주겠습니다.
			// File filePath = new File(path);
			// 중복이 발생할 수 있기때문에...... 파일명+날짜+ID+.파일확장자
			// UUID + 파일명 + .확장자
			// 아이디 + UUID + 파일명.확장자

			// 날짜 뽑기 SimpleDateFormat
			// Date date = new Date();
			// SimpleDateFormat sdf = new SimpleDateFormat("YYYYMMddHHmmss");
			// String dateTime = sdf.format(date);

			UUID uuid = UUID.randomUUID();
			// String realFileName = uuid.toString() + upfile.getOriginalFilename();
			// 다른 날짜 뽑기 형식
			LocalDateTime ldt = LocalDateTime.now();
			String format = ldt.format(DateTimeFormatter.ofPattern("YYYYMMddHHmmss"));
			// 날짜 + UUID + 실제 파일명으로 사용하겠습니다.
			String realFileName = format + uuid.toString() + upfile.getOriginalFilename();

			File newFileName = new File(path, realFileName);
			// 이제 파일 올립니다.
			try {
				// upfile.transferTo(newFileName);
			} catch (Exception e) {
				e.printStackTrace();
			}
			// System.out.println("저장 끝.");
			// FileCopyUtils를 사용하기 위해서는 오리지널 파일을 byte[]로 만들어야 합니다.
			try {
				FileCopyUtils.copy(upfile.getBytes(), newFileName);
			} catch (IOException e) {
				e.printStackTrace();
			}

			// #{upFile}, #{realFile}
			map.put("upFile", upfile.getOriginalFilename());
			map.put("realFile", realFileName);
		}

		map.put("mno", 4);// 로그인한 사람의 아이디를 담아주세요
		adminService.noticeWrite(map);
		return "redirect:/admin/notice";
	}

	@GetMapping("/mail")
	public String mail() {
		return "admin/mail";
	}

	// 2023-08-23
	@PostMapping("/mail")
	public String mail(@RequestParam Map<String, Object> map) throws EmailException {
		// System.out.println(map);
		// {title=메일을 보냅니다., to=ㅇㅇㅇㅇ, content=ㅇㅇㅇㅇ} :map의 정보가 이렇게 날아옴
		// util.simpleMailSender(map);텍스트만 보낼 수 있음
		util.htmlMailSender(map);// 텍스트, 사진,
		return "admin/mail";
	}

	// noticeDetail
	@ResponseBody
	@PostMapping("/noticeDetail")
	public String noticeDetail(@RequestParam("nno") int nno) {
		System.out.println(nno);

		// jackson 사용해보기
		ObjectNode json = JsonNodeFactory.instance.objectNode();

//		1. 데이터 베이스에 물어보기 : nno로 본문내용 가져오기
		String result = adminService.noticeDetail(nno);

//		2. jackson에 담아주세요.
		json.put("content", result);

		return json.toString();
	}

	@ResponseBody
	@PostMapping("/noticeHide")
	public String noticeHide(@RequestParam("nno") int nno) {
		int result = adminService.noticeHide(nno);
		ObjectNode json = JsonNodeFactory.instance.objectNode();
		json.put("result", result);

		return json.toString();
	}

	// 멀티 보드 만들기(평소와 다른 방법으로 씀)
	@RequestMapping(value = "/multiBoard", method = RequestMethod.GET)
	public String multiBoard(Model model) {
		List<Map<String, Object>> list = adminService.multiBoard();
		model.addAttribute("multiBoard", list);
		// System.out.println(list);

		return "admin/multiBoard";
	}

	@PostMapping("/multiBoard")
	public String multiBoard(@RequestParam Map<String, Object> map) {
		// System.out.println(map);{cateNum=6, name=5, comment=5}
		adminService.multiBoardInsert(map);
		return "redirect:/admin/multiBoard";
	}

	@GetMapping("/member")
	public ModelAndView member() {
		ModelAndView mv = new ModelAndView("admin/member");// admin/member로 이동하겠습니다.
		List<String> memberList = adminService.memberList();
		mv.addObject("memberList", memberList);
		// System.out.println(mv);
		return mv;
	}

	// location href 쓸 때는 get방식임 (post를 써주지 않음)
	@GetMapping("/gradeChange")
	public String gradeChange(@RequestParam Map<String, Object> map) {
		int result = adminService.gradeChange(map);
		// System.out.println(map);{mno=1, grade=1}
		// System.out.println(result);
		return "redirect:/admin/member";
	}

	@GetMapping("/post")
	// name과 value 둘 다 사용할 수 있음
	public String post(Model model, @RequestParam(name = "cate", required = false, defaultValue = "0") int cate,
			@RequestParam Map<String, Object> map) {// cate를 제외한 녀석들이 map에 담김 (카테는 위에 적어줬으니까)
		// 게시판 번호가 들어올 수 있음 (추후에)

		// 게시판 관리번호를 다 불러옴

		// map의 cate 값이 안 들어온다면 cate값을 0으로 넣어주기
		if (!(map.containsKey("cate")) || map.get("cate").equals(null) || map.get("cate").equals("")) {// map에 cate 값이
																										// 없다면
			map.put("cate", 0);
		}

		System.out.println("cate : " + cate);// cate : 0
		System.out.println("검색 : " + map);// 검색 : {searchV=2, cate=0}

		// 게시판 리스트 불러오기
		List<Map<String, Object>> boardlist = adminService.boardlist();
		model.addAttribute("boardlist", boardlist);

		// 게시판의 게시글 다 불러오기
		List<Map<String, Object>> list = adminService.post(map);
		model.addAttribute("list", list);
		System.out.println(list);
//[{mb_del=1, mb_content=<p>글을 써요<br></p>, m_no=3, mb_read=1, mb_date=2023-08-24 12:00:29.0, mb_board=2, mb_no=15, count=15, mb_title=자유게시판, m_id=park, b_catename=자유게시판}, {mb_del=1, mb_content=<p>문의사항~?<br></p>, m_no=3, mb_read=1, mb_date=2023-08-24 11:43:05.0, mb_board=4, mb_no=14, count=15, mb_title=문의사항을 올려봅니다., m_id=park, b_catename=문의사항}, {mb_del=1, mb_content=<p>글을 씁니다.<br></p>, m_no=3, mb_read=1, mb_date=2023-08-24 11:26:06.0, mb_board=2, mb_no=13, count=15, mb_title=자유게시판에 글을 써요, m_id=park, b_catename=자유게시판}, {mb_del=1, mb_content=<p>내용<br></p>, m_no=3, mb_read=1, mb_date=2023-08-17 16:41:41.0, mb_board=1, mb_no=12, count=15, mb_title=제목, m_id=park, b_catename=메인게시판}, {mb_del=1, mb_content=<p>엠엔오 오나요?<br></p>, m_no=3, mb_read=1, mb_date=2023-08-17 16:00:57.0, mb_board=1, mb_no=11, count=15, mb_title=테스트 해봅니다., m_id=park, b_catename=메인게시판}, {mb_del=1, mb_content=<p>최종입니다.<br></p>, m_no=3, mb_read=1, mb_date=2023-08-17 15:56:20.0, mb_board=1, mb_no=10, count=15, mb_title=최종 최종, m_id=park, b_catename=메인게시판}, {mb_del=1, mb_content=<p>써요<br></p>, m_no=3, mb_read=1, mb_date=2023-08-17 15:55:20.0, mb_board=1, mb_no=9, count=15, mb_title=글을, m_id=park, b_catename=메인게시판}, {mb_del=1, mb_content=<p>써요<br></p>, m_no=3, mb_read=1, mb_date=2023-08-17 15:54:26.0, mb_board=1, mb_no=8, count=15, mb_title=글을, m_id=park, b_catename=메인게시판}, {mb_del=1, mb_content=<p>써요<br></p>, m_no=3, mb_read=1, mb_date=2023-08-17 15:52:08.0, mb_board=1, mb_no=7, count=15, mb_title=글을, m_id=park, b_catename=메인게시판}, {mb_del=1, mb_content=<p>써요<br></p>, m_no=3, mb_read=1, mb_date=2023-08-17 15:52:08.0, mb_board=1, mb_no=6, count=15, mb_title=글을, m_id=park, b_catename=메인게시판}, {mb_del=1, mb_content=<p>키<br></p>, m_no=3, mb_read=1, mb_date=2023-08-17 15:50:54.0, mb_board=1, mb_no=5, count=15, mb_title=셀렉트키, m_id=park, b_catename=메인게시판}, {mb_del=1, mb_content=mb_no를 가져오기<br>, m_no=3, mb_read=1, mb_date=2023-08-17 15:48:32.0, mb_board=1, mb_no=4, count=15, mb_title=selectKey를 이용해서, m_id=park, b_catename=메인게시판}, {mb_del=1, mb_content=<p>dd<br></p>, m_no=3, mb_read=1, mb_date=2023-08-17 15:47:15.0, mb_board=1, mb_no=3, count=15, mb_title=dd, m_id=park, b_catename=메인게시판}, {mb_del=1, mb_content=<p>내용<br></p>, m_no=3, mb_read=1, mb_date=2023-08-17 15:35:09.0, mb_board=1, mb_no=2, count=15, mb_title=제목, m_id=park, b_catename=메인게시판}, {mb_del=1, mb_content=첫 글입니다., m_no=3, mb_read=1, mb_date=2023-08-17 11:09:14.0, mb_board=1, mb_no=1, count=15, mb_title=글을 씁니다., m_id=park, b_catename=메인게시판}]

		return "admin/post";
	}

	@ResponseBody
	@GetMapping("/detail")
	public String detail(@RequestParam("mbno") int mbno) {
		System.out.println(mbno);
		// 게시글 디테일 보여주기
		JSONObject json = new JSONObject();
		json.put("content", adminService.content(mbno));
		System.out.println(json);// {"content":"<p>글을 써요<br><\/p>"}
		return json.toString();
	}

	@GetMapping("/corona")
	public String corona(Model model) throws Exception {
		// String : 불변객체 한번 만들면 수정불가함
		// 스트링을 극복하고자 나온것이 아래
		// StringBuilder :
		// StringBuffer :
		StringBuilder urlBuilder = new StringBuilder(
				"http://apis.data.go.kr/1790387/covid19CurrentStatusKorea/covid19CurrentStatusKoreaJason"); /* URL */
		// + 뒤에 있는 내용을 utf-8로 바꿀거임
		urlBuilder.append("?" + URLEncoder.encode("serviceKey", "UTF-8")
				+ "=sKcjz%2FlyCeMnvLha1AuglT0MPWg1GEQ2oSvKGQJcHEi%2Br%2FynDyrvNa4OB%2BR5rTrWeanpLSVy0zTWuSDng%2FlL%2Bg%3D%3D"); /*
																																 * Service
																																 * Key
																																 */
		URL url = new URL(urlBuilder.toString());
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
		conn.setRequestProperty("Content-type", "application/json");
		System.out.println("Response code: " + conn.getResponseCode());
		BufferedReader rd;
		if (conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {// 통신이 됐을 경우
			rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));// 계속 내용을 넣을거임
		} else {// 통신이 안됐을 경우
			rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
		}
		StringBuilder sb = new StringBuilder();
		String line;
		while ((line = rd.readLine()) != null) {
			sb.append(line);
		}
		rd.close();
		conn.disconnect();
		// System.out.println(sb.toString());
// {"response":{"result":[{"rate_confirmations":"25.57","cnt_confirmations":"13154","cnt_severe_symptoms":"228","rate_deaths":"0.02","cnt_hospitalizations":"0","rate_severe_symptoms":"0.44","rate_hospitalizations":"0.00","cnt_deaths":"12","mmddhh":"8.28.00시"}],"resultCnt":"1","resultCode":"1","resultMsg":"조회성공"}}
		model.addAttribute("corona", sb.toString()); // 코로나라는 이름으로 sb.toString() 값이 나옴

		// 자바 스크립트 쓰지 않고 jsp에서 값을 찍어주기 위해서 데이터 변환하기
		// String to Json
		ObjectMapper objectMapper = new ObjectMapper();
		JsonNode jsonN = objectMapper.readTree(sb.toString());// readTree가 변환해줌

		System.out.println(jsonN.get("response").get("result").get(0));

		// Json to map
		Map<String, Object> result = objectMapper.readValue((jsonN.get("response").get("result").get(0)).toString(),
				new TypeReference<Map<String, Object>>() {
				});

		// TypeReference: 값을 뽑아내서 맵 형식으로 바꾸겠음

		System.out.println(result);
		model.addAttribute("result", result);

		return "admin/corona";

	}

	@GetMapping("/air2")
	public String air() throws Exception {
		StringBuilder urlBuilder = new StringBuilder(
				"http://apis.data.go.kr/B552584/ArpltnStatsSvc/getMsrstnAcctoRDyrg"); /* URL */
		urlBuilder.append("?" + URLEncoder.encode("serviceKey", "UTF-8") + "=sKcjz%2FlyCeMnvLha1AuglT0MPWg1GEQ2oSvKGQJcHEi%2Br%2FynDyrvNa4OB%2BR5rTrWeanpLSVy0zTWuSDng%2FlL%2Bg%3D%3D"); /* Service Key */
		urlBuilder.append("&returnType=xml"); /* xml 또는 json */
		urlBuilder.append("&numOfRows=100"); /* 한 페이지 결과 수 */
		urlBuilder.append("&pageNo=1"); /* 페이지번호 */
		urlBuilder.append("&inqBginDt=20230801"); /* 조회시작일자 */
		urlBuilder.append("&inqEndDt=20230829"); /* 조회종료일자 */
		urlBuilder.append("&msrstnName=" + URLEncoder.encode("강남구", "UTF-8")); /* 측정소명 */
		URL url = new URL(urlBuilder.toString());
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
		conn.setRequestProperty("Content-type", "application/json");
		System.out.println("Response code: " + conn.getResponseCode());//Response code: 200
		BufferedReader rd;
		if (conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
			rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		} else {
			rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
		}
		StringBuilder sb = new StringBuilder();
		String line;
		while ((line = rd.readLine()) != null) {
			sb.append(line);
		}
		rd.close();
		conn.disconnect();
		System.out.println(sb.toString());
		
		// String to xml
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document document = builder.parse(new InputSource(new StringReader(sb.toString())));//문서형식으로 바꾸기
		
		document.getDocumentElement().normalize();
		System.out.println("결과 : " + document);

		return "admin/air";
	}
	
	
	@GetMapping("/air")
	   public String air(Model model) throws Exception {
	      // String to xml
	      DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
	      DocumentBuilder builder = factory.newDocumentBuilder();
	      Document document = builder.parse("./air.xml");

	      //document.getDocumentElement().normalize();
	      System.out.println(document.getDocumentElement().getNodeName());
	      
	      NodeList list = document.getElementsByTagName("item");
	         //System.out.println("item length = " + list.getLength());
	         //System.out.println(list.toString());
	         ArrayList<Map<String, Object>> coronaList = new ArrayList<Map<String,Object>>();
	         for (int i = list.getLength() - 1; i >= 0; i--) {
	            NodeList childList = list.item(i).getChildNodes();
	            
	            Map<String, Object> value = new HashMap<String, Object>();
	            for (int j = 0; j < childList.getLength(); j++) {
	               Node node = childList.item(j);
	               if (node.getNodeType() == Node.ELEMENT_NODE) { 
	                  //System.out.println(node.getNodeName());
	                  //System.out.println(node.getTextContent());
	                  value.put(node.getNodeName(), node.getTextContent());
	               }
	            }
	            coronaList.add(value);
	         }
	         System.out.println("xml : " + coronaList);
	         model.addAttribute("list", coronaList);

	      return "/admin/air";
	   }

}
