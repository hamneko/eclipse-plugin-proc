package plugin.proc;

import org.eclipse.cdt.core.dom.ast.gnu.c.GCCLanguage;
import org.eclipse.cdt.core.model.ICLanguageKeywords;

public class PROCLanguage extends GCCLanguage {

	public PROCLanguage() {
	}

	@Override
	public String getId() {
		return "plugin.proc.editor";
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T getAdapter(Class<T> adapter) {
		if (adapter.isAssignableFrom(ICLanguageKeywords.class)) {
			return (T) this;
		}
		return super.getAdapter(adapter);
	}

	@Override
	public String[] getKeywords() {
		String[] keywords = super.getKeywords();
		String[] additionalKeywords = new String[] { "EXEC", "SQL" };
		String[] result = new String[keywords.length + additionalKeywords.length];
		System.arraycopy(keywords, 0, result, 0, keywords.length);
		System.arraycopy(additionalKeywords, 0, result, keywords.length, additionalKeywords.length);
		return result;
	}

}
