package edu.sjtu.lt_lab.mark;

import java.util.ArrayList;

import edu.hit.ir.ltpService.LTML;
import edu.hit.ir.ltpService.LTPOption;
import edu.hit.ir.ltpService.LTPService;
import edu.hit.ir.ltpService.Word;

/**
 * @author BennyChan
 * 利用哈工大句法分析器标注句法特征
 */
public class MarkSentenceStructure {
	private LTPService $ls = null;
	private LTML $ltml = null;
	private CutSentence $cs = null;

	public MarkSentenceStructure(CutSentence cs, String auth) {
		$ls = new LTPService(auth);
		$cs = cs;
	}

	/**
	 * 执行句法分析
	 */
	public boolean markSentenceStructure() {
		String weibo = "";
		// String newWeibo = "";
		// while (true) {
		weibo = $cs.getWeibo().getWeibo();
		// if (weibo.equals("")) {
		// break;
		// }
		if (weibo!=null&&!weibo.equals("")) {
			ArrayList<ArrayList<Word>> al = $cs.getSentences();
			int num = $cs.getSentNum();

			try {
				$ltml = new LTML();
				$ltml.setParagraphNumber(num);
				for (int k = 0; k < num; k++) {
					$ltml.addSentence(al.get(k), k);
				}
				$ltml = $ls.analyze(LTPOption.PARSER, $ltml);

				int sentNum = $ltml.countSentence();
				String[] pos = $cs.getPos();
				String[] word = $cs.getWords();

				String[] sentenceTag = new String[$cs.getWeiboWordCount()];
				for (int l = 0; l < $cs.getWeiboWordCount(); l++) {
					sentenceTag[l] = "0";
				}

				for (int l = 0; l < $cs.getWeiboWordCount() - 1; l++) {
					if ((pos[l].equals("tar") || word[l].equals("我") || word[l]
							.equals("我们"))) {
						if (pos[l + 1].startsWith("a")) {
							sentenceTag[l + 1] = "Wi_arg1";
						} else if ((l < $cs.getWeiboWordCount() - 2)
								&& (pos[l + 1].equals("vshi")
										|| pos[l + 1].equals("vyou")
										|| pos[l + 1].equals("vf") || pos[l + 1]
										.startsWith("rz"))) {
							if (pos[l + 2].startsWith("a")
									|| pos[l + 2].equals("n")
									|| pos[l + 2].equals("nl")
									|| pos[l + 2].equals("ng")) {
								sentenceTag[l + 2] = "Wi_cp_arg1";
							} else if ((l < $cs.getWeiboWordCount() - 3)
									&& pos[l + 2].equals("d")
									&& (pos[l + 3].startsWith("a")
											|| pos[l + 3].equals("n")
											|| pos[l + 3].equals("nl") || pos[l + 3]
											.equals("ng"))) {
								sentenceTag[l + 3] = "Wi_cp_arg1";
							}
						} else if ((l < $cs.getWeiboWordCount() - 2)
								&& (pos[l + 1].startsWith("v"))) {
							if (pos[l + 2].equals("d")
									|| pos[l + 2].equals("ad")
									|| pos[l + 2].equals("al")
									|| pos[l + 2].equals("bl")) {
								sentenceTag[l + 2] = "arg1_v_Wi";
							} else if ((l < $cs.getWeiboWordCount() - 3)
									&& pos[l + 2].equals("d")
									&& (pos[l + 3].equals("d")
											|| pos[l + 3].equals("ad")
											|| pos[l + 3].equals("al") || pos[l + 3]
											.equals("bl"))) {
								sentenceTag[l + 3] = "arg1_v_Wi";
							}
						} else if (pos[l + 1].equals("d")) {
							if ((l < $cs.getWeiboWordCount() - 2)
									&& pos[l + 2].startsWith("a")) {
								sentenceTag[l + 2] = "Wi_arg1";
							} else if ((l < $cs.getWeiboWordCount() - 3)
									&& (pos[l + 2].equals("vshi")
											|| pos[l + 2].equals("vyou")
											|| pos[l + 2].equals("vf") || pos[l + 2]
											.startsWith("rz"))) {
								if (pos[l + 3].startsWith("a")
										|| pos[l + 3].equals("n")
										|| pos[l + 3].equals("nl")
										|| pos[l + 3].equals("ng")) {
									sentenceTag[l + 3] = "Wi_cp_arg1";
								} else if ((l < $cs.getWeiboWordCount() - 4)
										&& pos[l + 3].equals("d")
										&& (pos[l + 4].startsWith("a")
												|| pos[l + 4].equals("n")
												|| pos[l + 4].equals("nl") || pos[l + 4]
												.equals("ng"))) {
									sentenceTag[l + 4] = "Wi_cp_arg1";
								}
							} else if ((l < $cs.getWeiboWordCount() - 3)
									&& (pos[l + 1].startsWith("v"))) {
								if (pos[l + 3].equals("d")
										|| pos[l + 3].equals("ad")
										|| pos[l + 3].equals("al")
										|| pos[l + 3].equals("bl")) {
									sentenceTag[l + 3] = "arg1_v_Wi";
								} else if ((l < $cs.getWeiboWordCount() - 4)
										&& pos[l + 3].equals("d")
										&& (pos[l + 4].equals("d")
												|| pos[l + 4].equals("ad")
												|| pos[l + 4].equals("al") || pos[l + 4]
												.equals("bl"))) {
									sentenceTag[l + 4] = "arg1_v_Wi";
								}
							}
						}
					}
				}

				System.out.println();
				int counter = 0;
				int boundary = 0;
				boolean hasTar = false;
				for (int i = 0; i < sentNum; ++i) {
					ArrayList<Word> sentWords = $ltml.getWords(i);

					/*
					 * System.out.println("第" + (i + 1) + "句词数：\t" +
					 * sentWords.size()); System.out.println("Boundary:	" +
					 * boundary);
					 */
					if (hasTar
							&& (sentWords.size() <= 2)
							&& (pos[counter].equals("vi") || pos[counter]
									.startsWith("a"))) {
						sentenceTag[boundary] = "Wi_arg";
					}

					if (hasTar
							&& sentWords.size() <= 3
							&& (pos[counter].equals("d"))
							&& (pos[counter + 1].equals("vi") || pos[counter + 1]
									.startsWith("a"))) {
						sentenceTag[boundary + 1] = "Wi_arg";
					}
					hasTar = false;

					for (int j = 0; j < sentWords.size(); ++j) {
						/*
						 * System.out.print(counter + "\t" + j + ":\t"); if
						 * ($ltml.hasWS()) { System.out.print("\t" +
						 * sentWords.get(j).getWS()); } if ($ltml.hasPOS()) {
						 * System.out.print("\t" + sentWords.get(j).getPOS()); }
						 * if ($ltml.hasNE()) { System.out.print("\t" +
						 * sentWords.get(j).getNE()); } if ($ltml.hasWSD()) {
						 * System.out.print("\t" + sentWords.get(j).getWSD() +
						 * "\t" + sentWords.get(j).getWSDExplanation()); }
						 */
						if (pos[counter++].equals("tar")) {
							hasTar = true;
							if ($ltml.hasWS()) {
								System.out.print("\t"
										+ sentWords.get(j).getWS());
							}
							if ($ltml.hasPOS()) {
								System.out.print("\t"
										+ sentWords.get(j).getPOS());
							}
							if ($ltml.hasParser()) {
								System.out.print("\t"
										+ sentWords.get(j).getParserParent()
										+ "\t"
										+ sentWords.get(j).getParserRelation());

								System.out
										.println("\t"
												+ word[boundary
														+ (sentWords
																.get(j)
																.getParserParent() < 0 ? 0
																: sentWords
																		.get(j)
																		.getParserParent())]
												+ "\t"
												+ pos[boundary
														+ (sentWords
																.get(j)
																.getParserParent() < 0 ? 0
																: sentWords
																		.get(j)
																		.getParserParent())]);
								if (pos[boundary
										+ (sentWords.get(j).getParserParent() < 0 ? 0
												: sentWords.get(j)
														.getParserParent())]
										.equals("v")
										|| pos[boundary
												+ (sentWords.get(j)
														.getParserParent() < 0 ? 0
														: sentWords
																.get(j)
																.getParserParent())]
												.equals("vd")
										|| pos[boundary
												+ (sentWords.get(j)
														.getParserParent() < 0 ? 0
														: sentWords
																.get(j)
																.getParserParent())]
												.equals("vn")
										|| pos[boundary
												+ (sentWords.get(j)
														.getParserParent() < 0 ? 0
														: sentWords
																.get(j)
																.getParserParent())]
												.equals("vl")) {
									if (sentWords.get(j).getParserRelation()
											.equals("SBV")) {
										sentenceTag[boundary
												+ (sentWords.get(j)
														.getParserParent() < 0 ? 0
														: sentWords
																.get(j)
																.getParserParent())] = "Wi_arg1";
									} else if (sentWords.get(j)
											.getParserRelation().equals("VOB")) {
										sentenceTag[boundary
												+ (sentWords.get(j)
														.getParserParent() < 0 ? 0
														: sentWords
																.get(j)
																.getParserParent())] = "Wi_arg2";
									}
								} else if (sentWords.get(j).getParserRelation()
										.equals("SBV")
										&& pos[boundary
												+ (sentWords.get(j)
														.getParserParent() < 0 ? 0
														: sentWords
																.get(j)
																.getParserParent())]
												.equals("vi")) {
									sentenceTag[boundary
											+ (sentWords.get(j)
													.getParserParent() < 0 ? 0
													: sentWords.get(j)
															.getParserParent())] = "Wi_it_arg1";
								}

							}
						}
						// System.out.println();
					}
					boundary += sentWords.size();
					// System.out.println("------------------------------------------------");
				}
				// newWeibo = "";
				for (int i = 0; i < sentenceTag.length; i++) {
					if (i > 1) {
						if (!sentenceTag[i].equals("0")) {
							if (word[i - 1].equals("不")
									|| word[i - 1].equals("没有")
									|| word[i - 2].equals("不")
									|| word[i - 2].equals("没有")) {
								sentenceTag[i] = "neg_" + sentenceTag[i];
							}
						}
					}
				}
				for (int i = 0; i < sentenceTag.length; i++) {
					$cs.getWeibo().getSentenceStructure()[i] = sentenceTag[i];
					/*
					 * if ($cs.getWeiboResult() != null) { newWeibo = newWeibo +
					 * word[i] + " " + pos[i] + " " + $cs.getEmotion()[i] + " "
					 * + $cs.getWordStructure()[i] + " " + sentenceTag[i] + " "
					 * + $cs.getWeiboResult()[i] + "\r"; } else { newWeibo =
					 * newWeibo + word[i] + " " + pos[i] + " " +
					 * $cs.getEmotion()[i] + " " + $cs.getWordStructure()[i] +
					 * " " + sentenceTag[i] + "\r"; }
					 */}
				/*
				 * System.out.print(newWeibo + "\r"); try { writeFile(newWeibo +
				 * "\r"); newWeibo = ""; } catch (IOException e) { // TODO
				 * Auto-generated catch block e.printStackTrace(); }
				 */} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return false;
			}
			/*
			 * try { writeFile(newWeibo); } catch (IOException e) { // TODO
			 * Auto-generated catch block e.printStackTrace(); }
			 */
			return true;
		} else {
			return false;
		}
		// if (!$cs.nextWeibo()) {
		// break;
		// }
		//
		// }

	}

	/*
	 * public void writeFile(String content) throws IOException { FileWriter fw
	 * = new FileWriter("E:/workplace/newData/韩寒方舟子.txt", true);
	 * fw.write(content); fw.flush(); fw.close();
	 * 
	 * }
	 */
}
