package plugin.proc;

import java.util.List;

import org.eclipse.cdt.core.dom.ast.IASTStatement;
import org.eclipse.cdt.core.dom.parser.c.ICParserExtensionConfiguration;
import org.eclipse.cdt.core.index.IIndex;
import org.eclipse.cdt.core.parser.EndOfFileException;
import org.eclipse.cdt.core.parser.IParserLogService;
import org.eclipse.cdt.core.parser.IScanner;
import org.eclipse.cdt.core.parser.ParserMode;
import org.eclipse.cdt.internal.core.dom.parser.ASTNode;
import org.eclipse.cdt.internal.core.dom.parser.BacktrackException;
import org.eclipse.cdt.internal.core.dom.parser.c.GNUCSourceParser;

import plugin.common.parser.scanner.ExecSqlPosition;
import plugin.common.parser.scanner.PluginTokenUtil;

@SuppressWarnings("restriction")
public class PROCSourceParser extends GNUCSourceParser {

	private List<ExecSqlPosition> execSqlPositions;

	public PROCSourceParser(IScanner scanner, ParserMode parserMode, IParserLogService logService,
			ICParserExtensionConfiguration config, IIndex index) {
		this(scanner, parserMode, logService, config, index, null);
	}

	public PROCSourceParser(IScanner scanner, ParserMode parserMode, IParserLogService logService,
			ICParserExtensionConfiguration config, IIndex index, List<ExecSqlPosition> execSqlPositions) {
		super(scanner, parserMode, logService, config, index);
		this.execSqlPositions = execSqlPositions;
	}

	@Override
	protected IASTStatement statement() throws EndOfFileException, BacktrackException {
		IASTStatement statement = super.statement();
		if (execSqlPositions == null) {
			return statement;
		}
		ASTNode node = (ASTNode) statement;
		if (PluginTokenUtil.inPosition(execSqlPositions, node.getOffset())) {
			return statement();
		}
		return statement;
	}

}
