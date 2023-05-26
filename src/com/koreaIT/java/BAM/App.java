package com.koreaIT.java.BAM;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.koreaIT.java.BAM.dto.Article;
import com.koreaIT.java.BAM.dto.Member;
import com.koreaIT.java.BAM.util.Util;

public class App {
	private List<Article> articles;
	private List<Member> members;
	
	App() {
		articles = new ArrayList<>();
		members = new ArrayList<>();
	}
	
	public void run() {
		System.out.println("== 프로그램 시작 ==");
		Scanner sc = new Scanner(System.in);
		
		makeTestData();
		
		int lastArticleId = 3;
		int lastMemberId = 0;
		
		while(true) {
			System.out.printf("명령어) ");	
			String cmd = sc.nextLine().trim();
			
			if(cmd.length() == 0) {
				System.out.println("명령어를 입력해주세요.");
				continue;
			}
			
			if(cmd.equals("exit")) {
				break;
			} 
			if(cmd.equals("member join")) {
				
				String regDate = Util.getNowDateStr();
				String loginId = null;
//				Boolean idCheck = true;
				
				while(true) {
					System.out.printf("로그인 아이디 : ");
					loginId = sc.nextLine().trim();
						
					if(isLoginIdDup(loginId) == false) {
						System.out.println("이미 사용중인 아이디입니다.");
						continue;
					}
//					for(Member member : members) {
//						if(member.id.equals(loginId)) {
//							System.out.println("아이디가 중복되었습니다.");
//							idCheck = false;
//						}
//					}
//					
//					if(idCheck == false) {
//						idCheck = true;
//						continue;
//					}
//					
					System.out.println(String.format("%s은(는) 사용가능한 아이디입니다.", loginId));
					break;
				}
				String loginPass = null;
				
				while(true) {
					System.out.printf("로그인 비밀번호 : ");
					loginPass = sc.nextLine().trim();
					System.out.printf("로그인 비밀번호 확인 : ");
					String passCheck = sc.nextLine().trim();
					
					if(loginPass.equals(passCheck) == false) {
						System.out.println("비밀 번호가 다릅니다.");
						continue;
					}
					break;
					
				}
			
				System.out.printf("이름 : ");
				String name = sc.nextLine();
				
				
				int sequence = lastMemberId + 1;
				lastMemberId = sequence;
				
				Member member = new Member(sequence, regDate, loginId, loginPass, name);
				
				members.add(member);
				
				System.out.println(String.format("%s 회원님 환영합니다.", name));
				
			}else if(cmd.equals("article write")) {
				
				String regDate = Util.getNowDateStr();
				
				System.out.printf("제목 : ");
				String title = sc.nextLine();
				System.out.printf("내용 : ");
				String body = sc.nextLine();
				int id = lastArticleId + 1;
				lastArticleId = id;
				
				
				Article article = new Article(id, regDate, title, body);
				
				articles.add(article);
				
				System.out.printf("%d번글이 생성되었습니다.\n", id);
				
			}else if(cmd.startsWith("article list")) {
				
				if (articles.size() == 0) {
					System.out.println("게시글이 없습니다");
					continue;
				}

				String searchKeyword = cmd.substring("article list".length()).trim();

				List<Article> forPrintArticles = articles;

				if (searchKeyword.length() > 0) {
					forPrintArticles = new ArrayList<>();

					for (Article article : articles) {
						if (article.title.contains(searchKeyword)) {
							forPrintArticles.add(article);
						}
					}

					if (forPrintArticles.size() == 0) {
						System.out.println("검색결과가 없습니다");
						continue;
					}
				}
				
				System.out.println("번호	|	제목		|		날짜		|	조회수	");
				
				if(forPrintArticles.size() != articles.size()) {
					System.out.println(String.format("검색어 : %s", searchKeyword));
				}
				
				for(int i = forPrintArticles.size() - 1; i >= 0; i--) {
					Article article = forPrintArticles.get(i);
					System.out.printf("%d	|      %s		|	%s	|	 %d	\n", article.id, article.title, article.regDate,article.count);
				}
//		split, startWith
			} else if(cmd.startsWith("article detail ")){
				String[] cmdBits = cmd.split(" ");
				int id = Integer.parseInt(cmdBits[2]);
				
//			boolean foundArticle = false;
				Article foundArticle = callData(id);
				
				
				if(foundArticle == null) {
					System.out.println(String.format("%d번 게시글은 존재하지 않습니다.", id));
					continue;
				}
				
				foundArticle.count += 1;
				
				System.out.println("== 게시글 상세보기 ==");
				System.out.println(String.format("번호 : %d", foundArticle.id));				
				System.out.println(String.format("날짜 : %s", foundArticle.regDate));
				System.out.println(String.format("제목 : %s", foundArticle.title));
				System.out.println(String.format("내용 : %s", foundArticle.body));
				System.out.println(String.format("조회수 : %d", foundArticle.count));
				
			}else if(cmd.startsWith("article delete ")){
				
				String[] cmdBits = cmd.split(" ");
				int id = Integer.parseInt(cmdBits[2]);
//			Article foundArticle = null;
				int foundIndex = -1;
				
				for(int i = 0; i < articles.size(); i++) {
					Article article = articles.get(i);
					
					if(article.id == id) {
						foundIndex = i;
//					foundArticle = article;
						break;
					}
				}
				if(foundIndex == -1) {
					System.out.println(String.format("%d번 게시글은 존재하지 않습니다.", id));
					continue;
				}
				
				articles.remove(foundIndex);
//			articles.remove(foundArticle);
				System.out.println(String.format("%d번 글이 삭제되었습니다.", id));
				
				
			}else if(cmd.startsWith("article modify ")){
				
				String[] cmdBits = cmd.split(" ");
				int id = Integer.parseInt(cmdBits[2]);
				Article foundArticle = callData(id);
				
				if(foundArticle == null) {
					System.out.println(String.format("%d번 게시글은 존재하지 않습니다.", id));
					continue;
				}
				
				System.out.printf("수정할 제목 : ");
				String title = sc.nextLine();
				System.out.printf("수정할 내용 : ");
				String body = sc.nextLine();
				foundArticle.title = title;
				foundArticle.body = body;
				
				System.out.println(String.format("%d번 글이 수정되었습니다.", id));
				
				
			}else {
				System.out.println(String.format("%s(은)는 존재하지 않는 명령어입니다.", cmd));
			}
			
		}
		
		sc.close();
		
		System.out.println("== 프로그램 끝 ==");
	}
	
		
	
	private void makeTestData() {
		System.out.println("테스트를 위한 데이터를 생성합니다.");
		
		articles.add(new Article(1, Util.getNowDateStr(), "test1", "test1", 10));
		articles.add(new Article(2, Util.getNowDateStr(), "test2", "test2", 20));
		articles.add(new Article(3, Util.getNowDateStr(), "test3", "test3", 30));
	}
	
	private Article callData(int id) {
		for(int i = 0; i < articles.size(); i++) {
			Article article = articles.get(i);
			
			if(article.id == id) {
				return article;
			}
		}
		return null;
	}
	
	private boolean isLoginIdDup(String loginId) {
		for(Member member : members) {
			
			if(member.id.equals(loginId)) {
				return false;
			}
		}
		return true;
	}
}
