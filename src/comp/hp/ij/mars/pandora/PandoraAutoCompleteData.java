package comp.hp.ij.mars.pandora;

public class PandoraAutoCompleteData {
	
		private String mszSearchItem = null;
		private String mszMusicToken = null;
		private String mszDisplay = null;
		
		public PandoraAutoCompleteData(String szSearchItem, String szMusicToken, String szDisplay){
			mszDisplay    = szDisplay;
			mszSearchItem = szSearchItem;
			mszMusicToken = szMusicToken;
		}
		public String getDisplay(){
			return mszDisplay;
		}
		public String getItem(){
			return mszSearchItem;
		}
		public String getMusicToken(){
			return mszMusicToken;
		}
	
}
