package edu.hit.ir.examples;

import java.util.ArrayList;

import edu.hit.ir.ltpService.LTML;
import edu.hit.ir.ltpService.LTPOption;
import edu.hit.ir.ltpService.LTPService;
import edu.hit.ir.ltpService.SRL;
import edu.hit.ir.ltpService.Word;

public class Ltp_example2 {

	/**
	 * <p>Title: main</p>
	 * <p>Description: </p>
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		LTPService ls = new LTPService("zqchenbin@gmail.com:20120419"); 		
		LTML ltml = new LTML();
		
		ArrayList<Word> wordList = new ArrayList<Word>();
		
		Word w1 = new Word();	
		w1.setWS("��ͦ");
		w1.setPOS("n");
		wordList.add(w1);

		Word w2 = new Word();	
		w2.setWS("����");
		w2.setPOS("nh");
		wordList.add(w2);

		Word w3 = new Word();	
		w3.setWS("��");
		w3.setPOS("d");
		wordList.add(w3);

		Word w4 = new Word();	
		w4.setWS("����");
		w4.setPOS("v");
		wordList.add(w4);
		
		Word w5 = new Word();	
		w5.setWS("������");
		w5.setPOS("nh");
		wordList.add(w5);
		
		Word w6 = new Word();	
		w6.setWS("ʵ��");
		w6.setPOS("d");
		wordList.add(w6);
		
		Word w7 = new Word();	
		w7.setWS("��");
		w7.setPOS("v");
		wordList.add(w7);
		
		Word w8 = new Word();	
		w8.setWS("һ");
		w8.setPOS("m");
		wordList.add(w8);
		
		Word w9 = new Word();	
		w9.setWS("��");
		w9.setPOS("q");
		wordList.add(w9);
		
		Word w10 = new Word();	
		w10.setWS("��");
		w10.setPOS("v");
		wordList.add(w10);
		
		Word w11 = new Word();	
		w11.setWS("��");
		w11.setPOS("a");
		wordList.add(w11);
		
		Word w12 = new Word();	
		w12.setWS("��");
		w12.setPOS("u");
		wordList.add(w12);
		
		Word w13 = new Word();	
		w13.setWS("û");
		w13.setPOS("v");
		wordList.add(w13);
		
		Word w14 = new Word();	
		w14.setWS("��");
		w14.setPOS("n");
		wordList.add(w14);
		
		Word w15 = new Word();	
		w15.setWS("��");
		w15.setPOS("v");
		wordList.add(w15);
		

		try {
			ltml.setParagraphNumber(2);
			ltml.addSentence(wordList, 0);
			
			ltml = ls.analyze(LTPOption.PARSER,ltml);
			
			int sentNum = ltml.countSentence();
			for(int i = 0; i< sentNum; ++i){
				ArrayList<Word> sentWords = ltml.getWords(i);
				for(int j = 0; j < sentWords.size(); ++j){
					if(ltml.hasWS()){
						System.out.print("\t" + sentWords.get(j).getWS());
					}
					if(ltml.hasPOS()){
						System.out.print("\t" + sentWords.get(j).getPOS());
					}
					if(ltml.hasNE()){
						System.out.print("\t" + sentWords.get(j).getNE());
					}
					if(ltml.hasWSD()){
						System.out.print("\t" + sentWords.get(j).getWSD() + "\t" + sentWords.get(j).getWSDExplanation());
					}
					if(ltml.hasParser()){
						System.out.print("\t" + sentWords.get(j).getParserParent() + "\t" + sentWords.get(j).getParserRelation());
					}
					
					if(ltml.hasSRL() && sentWords.get(j).isPredicate()){
						ArrayList<SRL> srls = sentWords.get(j).getSRLs();
						System.out.println();
						for(int k = 0; k <srls.size(); ++k){
							System.out.println("\t\t" + srls.get(k).type + "\t" + srls.get(k).beg + "\t" + srls.get(k).end);
						}
					}
					System.out.println();
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}

}
