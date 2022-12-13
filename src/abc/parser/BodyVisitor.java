package abc.parser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import abc.song.Note;
import abc.song.Pitch;
import abc.song.Body;
import abc.song.Song;

public class BodyVisitor extends AbcBodyBaseVisitor<Body> {
	static int ChordID = 1;
		
	public Body visitBody(AbcBodyParser.BodyContext ctx) {

		List<Note> orderedNoteList = new ArrayList<Note>();
		ctx.section().forEach( (s) -> {
				if (s.VOICE() != null && !s.VOICE().isEmpty()) {
					//System.out.println(s.VOICE());
				}
				s.measure().forEach( (m) -> {
					if (m.divider() != null && !m.divider().isEmpty()) {
						//System.out.println(m.divider().getText());
					}
					if (m.parts() != null && !m.parts().isEmpty()) {
						//System.out.println(m.parts().getText());
					}
					if (m.note() != null && !m.note().isEmpty()) {
						//System.out.println(m.note());
						m.note().forEach( (n) -> {
							if (n.ACCIDENTAL() != null) {
								//System.out.println(n.ACCIDENTAL().getText());
							}
							if (n.LETTER() != null) {
								//System.out.println(n.LETTER().getText());
								
								Pitch P = new Pitch(n.LETTER().getText());
								//new Pitch('C').toMidiNote();
								
								float L = 1;
								if (n.FRACTION() != null) {
									//System.out.println(n.NUMBER().getText());
									L = parseStringToFloat(n.FRACTION().getText());
									//System.out.println(L);
								}
								else if (n.NUMBER() != null) {
									L = parseStringToFloat(n.NUMBER().getText());
								}
								
								//System.out.println(L);

								String A = null;
								if (n.ACCIDENTAL() != null) {
									A = n.ACCIDENTAL().getText();
								}

								String V = null;
								if (s.VOICE() != null && !s.VOICE().isEmpty()) {
									V = s.VOICE().get(0).getText();
								}
								
								int C = 0;
								
								Note N = new Note(
										P, 
										L, 
										A, 
										C, 
										V
										);
								orderedNoteList.add(N);
							}

							if (n.NUMBER() != null) {
								//System.out.println(n.NUMBER().getText());
							}
							else if (n.FRACTION() != null) {
								//System.out.println(n.FRACTION().getText());
							}
						});
					}
					if (m.chord() != null && !m.chord().isEmpty()) {
						m.chord().forEach( (c) -> {
							if (c.note() != null && !c.note().isEmpty()) {
								//System.out.println(c.note());
								c.note().forEach( (n) -> {
									if (n.ACCIDENTAL() != null) {
										//System.out.println(n.ACCIDENTAL().getText());
									}
									if (n.LETTER() != null) {
										//System.out.println(n.LETTER().getText());
										
										Pitch P = new Pitch(n.LETTER().getText());
										//new Pitch('C').toMidiNote();
										
										float L = 1;
										if (n.FRACTION() != null) {
											//System.out.println(n.NUMBER().getText());
											L = parseStringToFloat(n.FRACTION().getText());
											//System.out.println(L);
										}
										else if (n.NUMBER() != null) {
											L = parseStringToFloat(n.NUMBER().getText());
										}

										String A = null;
										if (n.ACCIDENTAL() != null) {
											A = n.ACCIDENTAL().getText();
										}

										String V = null;
										if (s.VOICE() != null && !s.VOICE().isEmpty()) {
											V = s.VOICE().get(0).getText();
										}
										
										int C = ChordID;
										
										Note N = new Note(
												P, 
												L, 
												A, 
												C, 
												V
												);
										orderedNoteList.add(N);
									}

									if (n.NUMBER() != null) {
										//System.out.println(n.NUMBER().getText());
									}
									else if (n.FRACTION() != null) {
										//System.out.println(n.FRACTION().getText());
									}
								});
							}
							ChordID++;
						});
					}
					
					else if (m.tuplet() != null && !m.tuplet().isEmpty()) {
						m.tuplet().forEach( (t) -> {
							if (t.NUMBER() != null)
							{
								System.out.println(t.NUMBER().getText());
							}
							if (t.note() != null && !t.note().isEmpty()) {
								//System.out.println(c.note());
								t.note().forEach( (n) -> {
									if (n.ACCIDENTAL() != null) {
										//System.out.println(n.ACCIDENTAL().getText());
									}
									if (n.LETTER() != null) {
										//System.out.println(n.LETTER().getText());
										
										Pitch P = new Pitch(n.LETTER().getText());
										//new Pitch('C').toMidiNote();
										
										float L = 1;
										if (n.FRACTION() != null) {
											//System.out.println(n.NUMBER().getText());
											L = parseStringToFloat(n.FRACTION().getText());
											//System.out.println(L);
										}
										else if (n.NUMBER() != null) {
											L = parseStringToFloat(n.NUMBER().getText());
										}
										L=(L*2)/3;

										String A = null;
										if (n.ACCIDENTAL() != null) {
											A = n.ACCIDENTAL().getText();
										}

										String V = null;
										if (s.VOICE() != null && !s.VOICE().isEmpty()) {
											V = s.VOICE().get(0).getText();
										}
										
										int C = 0;
										
										Note N = new Note(
												P, 
												L, 
												A, 
												C, 
												V
												);
										orderedNoteList.add(N);
									}

									if (n.NUMBER() != null) {
										//System.out.println(n.NUMBER().getText());
									}
									else if (n.FRACTION() != null) {
										//System.out.println(n.FRACTION().getText());
									}
								});
							}

							//'(' NUMBER ( {counter<=$NUMBER.int}? note {counter++;} )*
							
						});
						//System.out.println(m.tuplet());
					}
				});
				if (s.divider() != null && !s.divider().isEmpty()) {
					//System.out.println(s.divider().getText());
				}
			}
		);
		
		Body bodyObj = new Body(orderedNoteList);
		return bodyObj;
	}
	

	Float parseStringToFloat(String ratio) {
		try {
			if (ratio.contains("/")) {
				String[] rat = ratio.split("/");
				if (ratio.length() == 3) {
					return Float.parseFloat(rat[0]) / Float.parseFloat(rat[1]);
				}
				else if (ratio.length() == 2) {
					return 1 / Float.parseFloat(rat[1]);
				}
				else {
					return (float) 0.5;
				}
			} else {
				return Float.parseFloat(ratio);
			}
		} catch(Exception e) {
			return null;
		}
	}
}
