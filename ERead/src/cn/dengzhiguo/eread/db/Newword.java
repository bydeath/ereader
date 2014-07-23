package cn.dengzhiguo.eread.db;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName="t_newword")
public class Newword {
	@DatabaseField(id=true)
	private String word;
	@DatabaseField
	private String en;
	@DatabaseField
	private String am;
	@DatabaseField
	private String voiceEn;
	@DatabaseField
	private String voiceAm;
	@DatabaseField
	private String voiceTts;
	@DatabaseField
	private String parts;
	public String getWord() {
		return word;
	}
	public void setWord(String word) {
		this.word = word;
	}
	public String getEn() {
		return en;
	}
	public void setEn(String en) {
		this.en = en;
	}
	public String getAm() {
		return am;
	}
	public void setAm(String am) {
		this.am = am;
	}
	public String getVoiceEn() {
		return voiceEn;
	}
	public void setVoiceEn(String voiceEn) {
		this.voiceEn = voiceEn;
	}
	public String getVoiceAm() {
		return voiceAm;
	}
	public void setVoiceAm(String voiceAm) {
		this.voiceAm = voiceAm;
	}
	public String getVoiceTts() {
		return voiceTts;
	}
	public void setVoiceTts(String voiceTts) {
		this.voiceTts = voiceTts;
	}
	public String getParts() {
		return parts;
	}
	public void setParts(String parts) {
		this.parts = parts;
	}
	
}
