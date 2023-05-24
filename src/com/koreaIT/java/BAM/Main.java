package com.koreaIT.java.BAM; // www.naver.com 으로 예시를 들면 com.naver 로 작성함 (회사마다 다름)

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
	public static void main(String[] args) {
		System.out.println("== 프로그램 시작 ==");
		Scanner sc = new Scanner(System.in);
		int lastArticleId = 0;
		
		List<Article> articles = new ArrayList<>();
		
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
				System.out.println("번호	|	제목	|	날짜");

				for(int i = articles.size() - 1; i >= 0; i--) {
					Article article = articles.get(i);
					System.out.printf("%d	|     %s	|	%s	\n", article.id, article.title, article.regDate);
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
}

class Article{
	int id;
	String regDate;
	String title;
	String body;
	
	Article(int id,String regDate, String title, String body){
		this.id = id;
		this.regDate = regDate;
		this.title = title;
		this.body = body;
	}
}