package ghidraal.langs;

import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.graalvm.polyglot.PolyglotException;
import org.graalvm.polyglot.Value;

import ghidraal.*;

public class Python3LangInfo extends LangInfo {

	public Python3LangInfo() {
		super(".py", "python", "#");
	}

	@Override
	protected ScriptingContext newScriptingContext() {
		return new Python3ScriptingContext();
	}

	private final class Python3ScriptingContext extends ScriptingContext {
		private Python3ScriptingContext() {
			super(Python3LangInfo.this);
		}

		@Override
		public Set<String> getMembersFromIntrospection(String varName) {
			try {
				Stream<Value> stream = Util.asStream(eval("dir(" + varName + ")"));
				if (stream != null) {
					return stream.map(v -> v.asString()).collect(Collectors.toSet());
				}
			}
			catch (PolyglotException e) {
				//
			}
			return Collections.emptySet();
		}
	}
}
