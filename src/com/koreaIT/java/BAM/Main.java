package com.koreaIT.java.BAM; // www.naver.com 으로 예시를 들면 com.naver 로 작성함 (회사마다 다름)

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
	
	static List<Article> articles;
	
	static {
		articles = new ArrayList<>();
	}
	
	public static void main(String[] args) {
		System.out.println("== 프로그램 시작 ==");
		Scanner sc = new Scanner(System.in);
		
		makeTestData();
		
		int lastArticleId = 3;

		
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
			
			if(cmd.equals("article write")) {
				
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
				
			}else if(cmd.equals("article list")) {
				
				if(articles.size() == 0) {
					System.out.println("게시글이 없습니다.");				
					continue;
				}
				System.out.println("번호	|	제목		|		날짜		|	조회수	");

				for(int i = articles.size() - 1; i >= 0; i--) {
					Article article = articles.get(i);
					System.out.printf("%d	|      %s		|	%s	|	 %d	\n", article.id, article.title, article.regDate,article.count);
				}
//			split, startWith
			} else if(cmd.startsWith("article detail ")){
				String[] cmdBits = cmd.split(" ");
				int id = Integer.parseInt(cmdBits[2]);
				
//				boolean foundArticle = false;
				Article foundArticle = null;
				
				for(int i = 0; i < articles.size(); i++) {
					Article article = articles.get(i);
					
					if(article.id == id) {
//						foundArticle = true;
						foundArticle = article;
						foundArticle.count += 1;
						break;
					}
				}
				if(foundArticle == null) {
					System.out.println(String.format("%d번 게시글은 존재하지 않습니다.", id));
					continue;
				}
				System.out.println("== 게시글 상세보기 ==");
				System.out.println(String.format("번호 : %d", foundArticle.id));				
				System.out.println(String.format("날짜 : %s", foundArticle.regDate));
				System.out.println(String.format("제목 : %s", foundArticle.title));
				System.out.println(String.format("내용 : %s", foundArticle.body));
				System.out.println(String.format("조회수 : %d", foundArticle.count));
				
			}else if(cmd.startsWith("article delete ")){
				
				String[] cmdBits = cmd.split(" ");
				int id = Integer.parseInt(cmdBits[2]);
//				Article foundArticle = null;
				int foundIndex = -1;
				
				for(int i = 0; i < articles.size(); i++) {
					Article article = articles.get(i);
					
					if(article.id == id) {
						foundIndex = i;
//						foundArticle = article;
						break;
					}
				}
				if(foundIndex == -1) {
					System.out.println(String.format("%d번 게시글은 존재하지 않습니다.", id));
					continue;
				}
				
				articles.remove(foundIndex);
//				articles.remove(foundArticle);
				System.out.println(String.format("%d번 글이 삭제되었습니다.", id));
				
				
			}else if(cmd.startsWith("article modify ")){
				
				String[] cmdBits = cmd.split(" ");
				int id = Integer.parseInt(cmdBits[2]);
				Article foundArticle = null;
				
				for(int i = 0; i < articles.size(); i++) {
					Article article = articles.get(i);
					
					if(article.id == id) {
						foundArticle = article;
						break;
					}
				}
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

	static void makeTestData() {
		System.out.println("테스트를 위한 데이터를 생성합니다.");
	
		articles.add(new Article(1, Util.getNowDateStr(), "test1", "test1", 10));
		articles.add(new Article(2, Util.getNowDateStr(), "test2", "test2", 20));
		articles.add(new Article(3, Util.getNowDateStr(), "test3", "test3", 30));
	}
	
	
}

class Article{
	int id;
	String regDate;
	String title;
	String body;
	int count;
	
	Article(int id,String regDate, String title, String body){
		this(id, regDate, title, body, 0);
	}
	
	Article(int id,String regDate, String title, String body, int count){
		this.id = id;
		this.regDate = regDate;
		this.title = title;
		this.body = body;
		this.count = count;
	}
}