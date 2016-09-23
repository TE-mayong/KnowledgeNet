package org.kmnet.com.fw.common.util.sqltemplate;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.Text;
import org.jdom2.input.SAXBuilder;

/**
 * SQL文生成テンプレートのJDOMを用いた実装クラス。
 * 
 * @version 1.1.2, 17 January, 2005
 * @since 1.0.0
 */
@SuppressWarnings("all")
public class SQLTemplateJDOMImpl implements SQLTemplate {

	/**
	 * コンストラクタ。
	 * 
	 * @param sqlsElement
	 *            &lt;sql-list&gt;要素
	 * @since 1.0.0
	 */
	public SQLTemplateJDOMImpl(Element sqlsElement) {

		this.sqlsElement = sqlsElement;
		populateVarTypeMap(sqlsElement);
		populateSqlElementMap(sqlsElement);
	}

	/**
	 * コンストラクタ。 こちらはデバッグ時などに使用する簡易用のコンストラクタである。
	 * createElementFromString()メソッドを用いてXMLを含む文字列を パースする。
	 * パースはSAXBuilderを使って行っている。 パースエラー時はJDOMExceptionを捕捉し、そのメッセージを持つ
	 * IllegalArgumentExceptionを投げる。 パースの方法を変えたい場合や、元のJDOMExceptionを補足したい
	 * 場合はこちらのコンストラクタは使わずに、JDOMのメソッドを使って 文字列をパースする処理を自分で書き、
	 * コンストラクタはSQLTemplateJDOImpl(org.jdom2.Element)のほうを 使用すること。
	 * 
	 * @param xmlText
	 *            &lt;sqls&gt;要素の文字列
	 * @param validate
	 *            バリデートフラグ
	 * @exception JDOMException
	 *                パースエラー時
     * @exception IOException
     *                I/Oエラー時
	 * @see #createElementFromString(String, boolean)
	 * @since 1.0.0
	 */
	public SQLTemplateJDOMImpl(String xmlText, boolean validate) throws JDOMException, IOException {

		this.sqlsElement = createElementFromString(xmlText, validate);
		populateVarTypeMap(sqlsElement);
		populateSqlElementMap(sqlsElement);
	}

	/**
	 * XML文字列をパースしてトップの要素を返す。 パースはSAXBuilderをバリデーションオフで使って行っている。
	 * 
	 * @param text
	 *            XML文字列
	 * @param validate
	 *            バリデートフラグ
	 * @return トップの要素
	 * @exception JDOMException
	 *                パースエラー時
     * @exception IOException
     *                I/Oエラー時
	 * @since 1.0.0
	 */
	protected Element createElementFromString(String text, boolean validate) throws JDOMException, IOException {

		SAXBuilder builder = new SAXBuilder(validate);
		Document doc = null;
		StringReader reader = new StringReader(text);
		try {
			doc = builder.build(reader);
		} finally {
			reader.close();
		}
		return doc.getRootElement();
	}

	/**
	 * コンストラクタ。 こちらはデバッグ時などに使用する簡易用のコンストラクタである。
	 * createElementFromFile()メソッドを用いてXMLファイルをパースする。 パースはSAXBuilderを使って行っている。
	 * パースエラー時はJDOMExceptionを捕捉し、そのメッセージを持つ IllegalArgumentExceptionを投げる。
	 * パースの方法を変えたい場合や、元のJDOMExceptionを補足したい 場合はこちらのコンストラクタは使わずに、JDOMのメソッドを使って
	 * 文字列をパースする処理を自分で書き、 コンストラクタはSQLTemplateJDOImpl(org.jdom2.Element)のほうを
	 * 使用すること。
	 * 
	 * @param file
	 *            XMLファイル
	 * @param validate
	 *            バリデートフラグ
	 * @exception JDOMException
	 *                パースエラー時
	 * @exception IOException
	 *                I/Oエラー時
	 * @see #createElementFromString(String, boolean)
	 * @since 1.0.0
	 */
	public SQLTemplateJDOMImpl(File file, boolean validate) throws JDOMException, IOException {

		this.sqlsElement = createElementFromFile(file, validate);
		populateVarTypeMap(sqlsElement);
		populateSqlElementMap(sqlsElement);
	}

	/**
	 * InputStream が対応するコンストラクタを追加
	 * @param in 入力ストリーム
     * @param validate
     *            バリデートフラグ
     * @exception JDOMException
     *                パースエラー時
     * @exception IOException
     *                I/Oエラー時
	 */
	public SQLTemplateJDOMImpl(InputStream in, boolean validate) throws JDOMException, IOException {

		this.sqlsElement = CreateElementFromStream(in, validate);
		populateVarTypeMap(sqlsElement);
		populateSqlElementMap(sqlsElement);
	}

    /**
     * InputStream が対応するメソッドを追加
     * @param fin 入力ストリーム
     * @param validate
     *            バリデートフラグ
     * @return XMLのElement
     * @exception JDOMException
     *                パースエラー時
     * @exception IOException
     *                I/Oエラー時
     */
	protected Element CreateElementFromStream(InputStream fin, boolean validate) throws JDOMException, IOException {

		SAXBuilder builder = new SAXBuilder(validate);
		Document doc = null;
		BufferedInputStream bin = null;
		try {
			bin = new BufferedInputStream(fin);
			doc = builder.build(bin);
		} finally {
			try {
				if (bin != null) {
					bin.close();
					fin = null;
				}
			} finally {
				if (fin != null) {
					fin.close();
				}
			}
		}
		return doc.getRootElement();
	}

	/**
	 * XMLファイルをパースしてトップの要素を返す。 パースはSAXBuilderを使って行っている。
	 * 
	 * @param file
	 *            XMLファイル
	 * @param validate
	 *            バリデートフラグ
	 * @return トップの要素
	 * @exception JDOMException
	 *                パースエラー時
	 * @exception IOException
	 *                I/Oエラー時
	 * @since 1.0.0
	 */
	protected Element createElementFromFile(File file, boolean validate) throws JDOMException, IOException {

		SAXBuilder builder = new SAXBuilder(validate);
		Document doc = null;
		BufferedInputStream bin = null;
		FileInputStream fin = new FileInputStream(file);
		try {
			bin = new BufferedInputStream(fin);
			doc = builder.build(bin);
		} finally {
			try {
				if (bin != null) {
					bin.close();
					fin = null;
				}
			} finally {
				if (fin != null) {
					fin.close();
				}
			}
		}
		return doc.getRootElement();
	}

	/**
	 * &lt;sql-list&gt;要素
	 */
	protected Element sqlsElement;

	/**
	 * &lt;sql&gt;要素のname属性値から&lt;sql&gt;要素への対応マップ
	 */
	protected Map sqlElementMap;

	/**
	 * &lt;sql&gt;要素または&lt;procedure&gt;のid属性値から要素への 対応マップを作成する。
	 * これは指定したid属性値の要素を見つけるのに毎回DOMをたどるのは 遅いので、予めマップ形式にしておくためである。
	 * 
	 * @param parentElem
	 *            &lt;sql-list&gt;要素
	 * @since 1.0.0
	 */
	protected void populateSqlElementMap(Element parentElem) {

		sqlElementMap = new HashMap();
		List elems = parentElem.getChildren();
		if (elems != null) {
			for (Iterator it = elems.iterator(); it.hasNext();) {
				Element elem = (Element) it.next();
				String name = elem.getName();
				if ("sql".equals(name) || "procedure".equals(name)) {
					String id = elem.getAttributeValue("id");
					sqlElementMap.put(id, elem);
				}
			}
		}
	}

	/**
	 * &lt;var-type&gt;要素のvar属性値からtype属性値への対応マップ
	 */
	protected Map varTypeMap;

	/**
	 * &lt;sql&gt;要素または&lt;procedure&gt;のid属性値から要素への 対応マップを作成する。
	 * これは指定したid属性値の要素を見つけるのに毎回DOMをたどるのは 遅いので、予めマップ形式にしておくためである。
	 * 
	 * @param parentElem
	 *            &lt;sql-list&gt;要素
	 * @since 1.0.0
	 */
	protected void populateVarTypeMap(Element parentElem) {

		List varTypesElems = parentElem.getChildren("var-types");
		if (varTypesElems == null || varTypesElems.isEmpty()) {
			return;
		}
		if (varTypesElems.size() != 1) {
			throw new IllegalArgumentException("Only one <var-types> is allowed.");
		}
		varTypeMap = new HashMap();
		Element varTypesElem = (Element) varTypesElems.get(0);
		List elems = varTypesElem.getChildren("var-type");
		if (elems != null) {
			for (Iterator it = elems.iterator(); it.hasNext();) {
				Element elem = (Element) it.next();
				String varName = elem.getAttributeValue("var");
				String typeName = elem.getAttributeValue("type");
				int type = TypesUtils.getValue(typeName);
				varTypeMap.put(varName, new Integer(type));
			}
		}
	}

	/**
	 * varTypeMapから変数の型を取得する。 varTypeMapに登録されていない場合はjava.sql.Types.VARCHARを返する。
	 * 
	 * @param varName
	 *            変数名（var属性値）
	 * @return 変数の型
	 * @since 1.0.0
	 */
	protected int getVarType(String varName) {

		Integer typeObj = (Integer) varTypeMap.get(varName);
		return typeObj == null ? Types.VARCHAR : typeObj.intValue();
	}

	/**
	 * SQLインスタンスを生成する。 実際の処理のほとんどは、&lt;sql&gt;要素の場合はhandleContent()、
	 * &lt;procedure&gt;要素の場合はhandleProcedureElement()で行われる。
	 * 
	 * @param id
	 *            どのSQLかを特定するID
	 * @param params
	 *            バインド変数のパラメータ群
	 * @return SQLインスタンス
	 * @exception IllegalArgumentException
	 *                XMLまたはパラメータが不正な場合
	 * @see #handleContent(java.util.List, java.util.Map, java.util.List)
	 * @see #handleProcedureElement(org.jdom2.Element, java.util.Map,
	 *      java.util.List)
	 * @since 1.0.0
	 */
	public SQL createSQL(String id, Map params) {

		Element elem = (Element) sqlElementMap.get(id);
		if (elem == null) {
			throw new IllegalArgumentException(ERRMSG001);
		}

		String text = null;
		List varList = new ArrayList();
		if ("sql".equals(elem.getName())) {
			text = handleContent(elem.getContent(), params, varList);
		} else if ("procedure".equals(elem.getName())) {
			text = handleProcedureElement(elem, params, varList);
		}

		BindVariable[] vars = new BindVariable[varList.size()];
		vars = (BindVariable[]) varList.toArray(vars);
		SQL sql = new SQL();
		sql.setText(text);
		sql.setVariables(vars);
		return sql;
	}

	/**
	 * 要素のコンテントを処理してSQL文を組み立てると同時に バインド変数リストに必要なバインド変数を追加する。
	 * <p>
	 * コンテントとしては文字列とXML要素が混在したものを想定していて、 文字列はそのままを、XML要素は処理した結果のSQL断片を連結していき
	 * その結果をSQL文字列として返す。
	 * </p>
	 * <p>
	 * なお、文字列は前後の空白の除去は行っていない。これはSQL文中に リテラル文字列で継続行を使っているケースを想定すると、空白の除去
	 * を行うためには、各データベースごとに独自の方法でSQLをパースしなく てはいけなくなるためです。しかし、それを実装するのは大変ですし、
	 * 実装できたとしてもその処理にCPU時間やメモリを消費するのは無駄で あると判断し、その実装は行わないことにした。
	 * </p>
	 * <p>
	 * この結果として、XMLを見やすくインデントして記述していると、生成 されるSQL文には無駄な空白が含まれることになりますがこれは仕様とします。
	 * どうしてもSQL文に無駄な空白を含めたくない場合は、XMLで記述する際に 改行や空白を入れずに連続して記述すること。
	 * </p>
	 * 
	 * @param content
	 *            コンテント
	 * @param params
	 *            指定されたパラメータ群
	 * @param varList
	 *            バインド変数リスト
	 * @return SQL文字列
	 * @exception IllegalArgumentException
	 *                XMLまたはパラメータが不正な場合
	 * @see #handleElement(org.jdom2.Element, java.util.Map, java.util.List)
	 * @since 1.0.0
	 */
	protected String handleContent(List content, Map params, List varList) {

		if (content == null) {
			return null;
		}
		StringBuffer buf = new StringBuffer();
		for (Iterator it = content.iterator(); it.hasNext();) {
			Object part = it.next();
			if (part instanceof Text) {
				buf.append(((Text) part).getValue());
			} else if (part instanceof Element) {
				String text = handleElement((Element) part, params, varList);
				if (text == null) {
					continue;
				}
				buf.append(text);
			}
		}
		return new String(buf);
	}

	/**
	 * &lt;sql&gt;要素のサブ要素１つを処理する。 サブ要素は次のいずれかでなければならない。
	 * <ul>
	 * <li>&lt;where&gt;</li>
	 * <li>&lt;cond-list&gt;</li>
	 * <li>&lt;cond&gt;</li>
	 * <li>&lt;expr-list&gt;</li>
	 * <li>&lt;expr&gt;</li>
	 * <li>&lt;value&gt;</li>
	 * <li>&lt;switch&gt;</li>
	 * </ul>
	 * それ以外の要素の場合はIllegalArgumentExceptionが投げられる。
	 * 
	 * @param elem
	 *            &lt;sql&gt;要素のサブ要素
	 * @param params
	 *            パラメータ群
	 * @param varList
	 *            バインド変数リスト
	 * @return SQL文字列
	 * @exception IllegalArgumentException
	 *                XMLまたはパラメータが不正な場合
	 * @see #handleWhereElement(org.jdom2.Element, java.util.Map, java.util.List)
	 * @see #handleCondListElement(org.jdom2.Element, java.util.Map,
	 *      java.util.List)
	 * @see #handleCondElement(org.jdom2.Element, java.util.Map, java.util.List)
	 * @see #handleExprListElement(org.jdom2.Element, java.util.Map,
	 *      java.util.List)
	 * @see #handleExprElement(org.jdom2.Element, java.util.Map, java.util.List)
	 * @see #handleValueElement(org.jdom2.Element, java.util.Map, java.util.List)
	 * @see #handleSwitchElement(org.jdom2.Element, java.util.Map,
	 *      java.util.List)
	 * @since 1.0.0
	 */
	protected String handleElement(Element elem, Map params, List varList) {

		String name = elem.getName();
		if ("where".equals(name)) {
			return handleWhereElement(elem, params, varList);
		} else if ("cond-list".equals(name)) {
			return handleCondListElement(elem, params, varList);
		} else if ("cond".equals(name)) {
			return handleCondElement(elem, params, varList);
		} else if ("expr-list".equals(name)) {
			return handleExprListElement(elem, params, varList);
		} else if ("expr".equals(name)) {
			return handleExprElement(elem, params, varList);
		} else if ("value".equals(name)) {
			return handleValueElement(elem, params, varList);
		} else if ("switch".equals(name)) {
			return handleSwitchElement(elem, params, varList);
		} else {
			return null;
		}
	}

	/**
	 * &lt;procedure&gt;要素１つを処理する。 コンテントは次のいずれかでなければならない。
	 * <ul>
	 * <li>&lt;call-text&gt;</li>
	 * <li>&lt;param-list&gt;</li>
	 * </ul>
	 * 
	 * @param elem
	 *            &lt;procedure&gt;要素のサブ要素
	 * @param params
	 *            パラメータ群
	 * @param varList
	 *            バインド変数リスト
	 * @return SQL文字列
	 * @exception IllegalArgumentException
	 *                XMLまたはパラメータが不正な場合
	 * @see #handleCallTextElement(org.jdom2.Element, java.util.Map,
	 *      java.util.List)
	 * @see #handleParamListElement(org.jdom2.Element, java.util.Map,
	 *      java.util.List)
	 * @since 1.0.0
	 */
	protected String handleProcedureElement(Element elem, Map params, List varList) {

		List content = elem.getContent();
		if (content == null) {
			return null;
		}
		StringBuffer buf = new StringBuffer();
		for (Iterator it = content.iterator(); it.hasNext();) {
			Object part = it.next();
			if (part instanceof Element) {
				Element child = (Element) part;
				String text = null;
				if ("call-text".equals(child.getName())) {
					text = handleCallTextElement(child, params, varList);
				} else if ("param-list".equals(child.getName())) {
					text = handleParamListElement(child, params, varList);
				}
				if (text != null) {
					buf.append(text);
				}
			}
		}
		return new String(buf);

	}

	/**
	 * &lt;where&gt;要素を処理する。
	 * <p>
	 * &lt;where&gt;要素には必ずjoint属性を指定すること。 未指定の場合はIllegalArgumentExceptionが投げられる。
	 * </p>
	 * <p>
	 * &lt;where&gt;要素ではサブ要素に対して順次handleElement()
	 * を実行して、それぞれの結果をjoint属性値の前後に空白を付加 したもので繋いで先頭に"where"の前後に空白を付加したものを
	 * 追加したものを結果のSQL文字列として返す。ただし、 サブ要素の全ての処理結果が空文字列だった場合は、このメソッド の戻り値も空文字列を返す。
	 * </p>
	 * <p>
	 * 一方で処理中に発見したバインド変数は引数のバインド変数リスト に追加していく。
	 * </p>
	 * 
	 * @param elem
	 *            &lt;where&gt;要素
	 * @param params
	 *            パラメータ群
	 * @param varList
	 *            バインド変数リスト
	 * @return SQL文字列
	 * @exception IllegalArgumentException
	 *                XMLまたはパラメータが不正な場合
	 * @see #handleElement(org.jdom2.Element, java.util.Map, java.util.List)
	 * @since 1.0.0
	 */
	protected String handleWhereElement(Element elem, Map params, List varList) {

		String joint = elem.getAttributeValue("joint");

		List children = elem.getChildren();
		if (children == null) {
			return null;
		}
		StringBuffer buf = new StringBuffer();
		String curJoint = "where"; // 最初の空でない要素の前に入れるのは"where"
		for (Iterator it = children.iterator(); it.hasNext();) {
			Element child = (Element) it.next();
			String text = handleElement(child, params, varList);
			if (text == null || text.trim().length() == 0) {
				continue;
			}

			buf.append(' ');
			buf.append(curJoint);
			buf.append(' ');
			buf.append(text);

			// 2つめ以降の空でない要素の前に入れるのは"joint"属性に指定した値
			curJoint = joint;
		}
		String ret = new String(buf);
		if (ret.length() == 0) {
			return null;
		} else {
			return ret;
		}
	}

	/**
	 * &lt;cond&gt;要素を処理する。
	 * <p>
	 * &lt;cond&gt;要素のvar属性値にはパラメータ名のカンマ区切りリスト を指定する（省略可）。
	 * </p>
	 * <p>
	 * var属性値を設定されたパラメータ名の中に、引数に指定された パラメータ群に含まれないか値がnullのものがある場合は、この
	 * メソッドはnullを返す。
	 * </p>
	 * <p>
	 * var属性値が省略されたか、設定されたパラメータ名全てが 引数のパラメータ群に含まれ且つ値が非nullである場合は、
	 * 以下のルールでSQL文字列を生成してこのメソッドの戻り値として 返す。また、一方でバインド変数を発見するごとに バインド変数リストに追加する。
	 * </p>
	 * <ul>
	 * <li>
	 * &lt;cond&gt;要素にrepeat属性が"true"に指定されていない場合は、
	 * &lt;cond&gt;要素のテキストそのままをメソッドの戻り値として 返す。
	 * 一方バインド変数は、&lt;cond&gt;要素のテキスト中の ?の数だけ追加される。この際、バインド変数の名前は
	 * var属性値に指定した空白区切りリストから切り出された パラメータ名（先頭から順番に使用されます）になり、バインド
	 * 変数値は引数のパラメータ群内でそのパラメータ名に対応する値 になる。
	 * なお、?の出現数よりカンマ区切りリストの要素数が 多いのは構わない。余りはバインド変数としては使われず単に
	 * 無視される。余りのパラメータは、nullを返すか直前に記述した ルールで作成した文字列を返すかを切り替えるためのみに使われる ことになる。
	 * また、like属性が指定されている場合は、modifyWildCard() メソッドによりバインド変数値が加工される。
	 * </li>
	 * <li>
	 * &lt;cond&gt;要素にrepeat属性が"true"に指定されている場合は、 次の一点を除いては、無指定の場合と同じである。
	 * &lt;expr&gt;要素のテキスト内で?を見つけたときに、 対応するパラメータ値がjava.util.ListまたはObject[]の
	 * サブクラスだった場合、SQL文字列としてはその?をパラメータ値 の要素数分のカンマリストに置換して（例：要素数が2なら?,?）
	 * その結果をこのメソッドの戻り値として返す。その際、 バインド変数名は、「パラメータ名[要素添え字]」（例： PARAM[0]）という形式になる。
	 * </li>
	 * </ul>
	 * 
	 * @param elem
	 *            &lt;cond&gt;要素
	 * @param params
	 *            パラメータ群
	 * @param varList
	 *            バインド変数リスト
	 * @return SQL文字列
	 * @see #containsAllNonNullValueKeys(java.util.Map, String[])
	 * @see #modifyWildCard(Object, String, String)
	 * @see #getAttributeBooleanValue(org.jdom2.Element, String)
	 * @since 1.0.0
	 */
	protected String handleCondElement(Element elem, Map params, List varList) {

		String[] varNames = tokenize(elem.getAttributeValue("var"), " ");
		if (!containsAllNonNullValueKeys(params, varNames)) {
			return null;
		}

		List content = elem.getContent();
		if (content == null) {
			return null;
		}
		StringBuffer buf = new StringBuffer();
		int varIndex = 0;
		String like = elem.getAttributeValue("like");
		String escape = elem.getAttributeValue("escape");
		boolean repeat = getAttributeBooleanValue(elem, "repeat");
		for (Iterator it = content.iterator(); it.hasNext();) {
			Object part = it.next();
			if (part instanceof Text) {
				String text = ((Text) part).getValue();
				varIndex = handleCondElementPCData(elem, params, text, varNames, like, escape, repeat, varList, buf,
						varIndex);
			} else if (part instanceof Element) {
				String text = handleElement((Element) part, params, varList);
				if (text == null) {
					continue;
				}
				buf.append(text);
			}
		}
		return new String(buf);
	}

	/**
	 * &lt;cond&gt;要素のPCDataを処理する。
	 * 
	 * @param elem
	 *            &lt;cond&gt;要素
	 * @param params
	 *            パラメータ集合
	 * @param text
	 *            PCDataテキスト
	 * @param varNames
	 *            変数名配列
	 * @param like
	 *            SQLのlikeタイプ
	 * @param escape
	 *            SQLのescape文字
	 * @param repeat
	 *            repeat属性が指定されているか
	 * @param varList
	 *            バインド変数リスト
	 * @param buf
	 *            SQL文字列バッファ
	 * @param varIndex
	 *            変数インデクス
	 * @return 変更後の変数インデクス
	 * @since 1.0.0
	 */
	protected int handleCondElementPCData(Element elem, Map params, String text, String[] varNames, String like,
			String escape, boolean repeat, List varList, StringBuffer buf, int varIndex) {

		for (int i = 0; i < text.length(); i++) {
			char ch = text.charAt(i);
			if (ch != '?') {
				buf.append(ch);
				continue;
			}
			String name = varNames[varIndex++];
			Object value = getParamValue(params, name);
			if (repeat && (value instanceof List || value instanceof Object[])) {
				List values = null;
				if (value instanceof List) {
					values = (List) value;
				} else {
					values = Arrays.asList((Object[]) value);
				}

				for (int j = 0; j < values.size(); j++) {
					Object elemValue = values.get(j);
					if (like != null) {
						elemValue = modifyWildCard(elemValue, escape, like);
					}

					BindVariable var = new BindVariable();
					var.setName(name + "[" + j + "]");
					var.setValue(elemValue);
					if (varTypeMap != null) {
						var.setJdbcType(getVarType(name));
					}
					varList.add(var);

					if (j > 0) {
						buf.append(',');
					}
					buf.append('?');
				}
			} else {
				if (like != null) {
					value = modifyWildCard(value, escape, like);
				}
				BindVariable var = new BindVariable();
				var.setName(name);
				var.setValue(value);
				if (varTypeMap != null) {
					var.setJdbcType(getVarType(name));
				}
				varList.add(var);

				buf.append('?');
			}
		}
		return varIndex;
	}

	/**
	 * パラメータ値に含まれるエスケープ対象文字をエスケープし、 like検索のワイルドカード用の文字を付加する。
	 * 引数のエスケープ文字にnullを指定した場合はエスケープは行わない。
	 * 引数のlikeにnullを指定した場合はワイルドカード用の文字の付加は行わない。
	 * 
	 * @param value
	 *            パラメータ値
	 * @param escape
	 *            エスケープ文字
	 * @param like
	 *            ワイルドカード検索の種別
	 * @return 加工後のパラメータ値
	 * @see #escapeSQLWildCard(String, String)
	 * @see #addSQLWildCard(String, String)
	 * @since 1.0.0
	 */
	protected String modifyWildCard(Object value, String escape, String like) {

		String strValue = (value == null ? null : value.toString());
		if (escape != null) {
			strValue = escapeSQLWildCard(strValue, escape);
		}
		if (like != null) {
			strValue = addSQLWildCard(strValue, like);
		}
		return strValue;
	}

	/**
	 * パラメータ値中に含まれるエスケープ対象文字をエスケープする。 エスケープ対象文字は'%'と'_'とエスケープ文字自身である。
	 * 
	 * @param value
	 *            パラメータ値
	 * @param escape
	 *            エスケープ文字
	 * @return エスケープ後のパラメータ値
	 * @since 1.0.0
	 */
	protected String escapeSQLWildCard(String value, String escape) {

		char escapeCh = escape.charAt(0);
		StringBuffer buf = new StringBuffer(value.length());
		for (int i = 0; i < value.length(); i++) {
			char ch = value.charAt(i);
			if (ch == '%' || ch == '_' || ch == escapeCh) {
				buf.append(escapeCh);
			}
			buf.append(ch);
		}
		return buf.toString();
	}

	/**
	 * SQLのlike検索のパラメータ値にワイルドカード用の文字を付加する。
	 * <ul>
	 * <li>likeTypeに"?%"を指定した場合、パラメータ値の最後に"%"を追加します（前方一致検索）</li>
	 * <li>likeTypeに"%?%"を指定した場合、パラメータ値の前後に"%"を追加します（中間一致検索）</li>
	 * <li>likeTypeに"%?"を指定した場合、パラメータ値の先頭に"%"を追加します（後方一致検索）</li>
	 * <li>likeTypeがそれ以外の場合、パラメータ値をそのまま返します</li>
	 * </ul>
	 * 
	 * @param value
	 *            パラメータ値
	 * @param likeType
	 *            ワイルドカード検索の種別
	 * @return 変更後のパラメータ値
	 * @since 1.0.0
	 */
	protected String addSQLWildCard(String value, String likeType) {

		if (likeType.equals("?%")) {
			return value + "%";
		} else if (likeType.equals("%?%")) {
			return "%" + value + "%";
		} else if (likeType.equals("%?")) { // [0501001] 2005/01/17 Modified
			return "%" + value;
		} else {
			return value;
		}
	}

	/**
	 * &lt;cond-list&gt;要素を処理する。
	 * <p>
	 * &lt;cond-list&gt;要素には必ずjoint属性を指定すること。
	 * 未指定の場合はIllegalArgumentExceptionが投げられる。
	 * </p>
	 * <p>
	 * &lt;cond-list&gt;要素ではサブ要素に対して順次handleElement()を実行して、
	 * それぞれの結果をjoint属性値の前後に空白を付加したもので繋いで全体を括弧で 囲ったものを結果のSQL文字列として返す。ただし、
	 * サブ要素の全ての処理結果が空文字列だった場合は、このメソッド の戻り値も空文字列を返す。
	 * </p>
	 * <p>
	 * 一方でバインド変数を発見するごとにバインド変数リストに追加していく。
	 * </p>
	 * 
	 * @param elem
	 *            &lt;cond-list&gt;要素
	 * @param params
	 *            パラメータ群
	 * @param varList
	 *            バインド変数リスト
	 * @return SQL文字列
	 * @exception IllegalArgumentException
	 *                XMLまたはパラメータが不正な場合
	 * @see #handleElement(org.jdom2.Element, java.util.Map, java.util.List)
	 * @see #getAttributeBooleanValue(org.jdom2.Element, String)
	 * @since 1.0.0
	 */
	protected String handleCondListElement(Element elem, Map params, List varList) {

		String joint = elem.getAttributeValue("joint");

		List children = elem.getChildren();
		if (children == null) {
			return null;
		}

		StringBuffer buf = new StringBuffer();
		String curJoint = null;
		for (Iterator it = children.iterator(); it.hasNext();) {
			Element child = (Element) it.next();
			String text = handleElement(child, params, varList);
			if (text == null || text.trim().length() == 0) {
				continue;
			}

			if (curJoint != null) {
				buf.append(' ');
				buf.append(curJoint);
				buf.append(' ');
			}
			buf.append(text);

			// 2つめ以降の空でない要素の前に入れるのは"joint"属性に指定した値
			curJoint = joint;
		}
		String ret = new String(buf);
		if (ret.length() == 0) {
			return null;
		} else {
			return "(" + ret + ")";
		}
	}

	/**
	 * &lt;expr&gt;要素を処理する。
	 * <p>
	 * &lt;expr&gt;要素のvar属性値にはパラメータ名のカンマ区切りリスト を指定する（省略可）。
	 * </p>
	 * <p>
	 * var属性値を設定されたパラメータ名の中に、引数に指定された パラメータ群に含まれないものがある場合は、このメソッドはnull を返す。
	 * </p>
	 * var属性値が省略されたか、設定されたパラメータ名全てが 引数のパラメータ群に含まれる場合は、以下のルールで
	 * SQL文字列を生成してこのメソッドの戻り値として返す。 また、一方でバインド変数を発見するごとにバインド変数リストに 追加する。
	 * <ul>
	 * <li>
	 * &lt;expr&gt;要素にrepeat属性が"true"に指定されていない場合は、
	 * &lt;expr&gt;要素のテキストそのままをメソッドの戻り値として 返す。
	 * 一方バインド変数は、&lt;expr&gt;要素のテキスト中の ?の数だけ追加される。この際、バインド変数の名前は
	 * var属性値に指定した空白区切りリストから切り出された パラメータ名（先頭から順番に使用されます）になり、バインド
	 * 変数値は引数のパラメータ群内でそのパラメータ名に対応する値 になる。
	 * なお、?の出現数より空白区切りリストの要素数が 多いのは構わない。余りはバインド変数としては使われず単に
	 * 無視されます。余りのパラメータは、nullを返すか直前に記述した ルールで作成した文字列を返すかを切り替えるためのみに使われる ことになる。
	 * </li>
	 * <li>
	 * &lt;expr&gt;要素にrepeat属性が"true"に指定されている場合は、 次の一点を除いては、無指定の場合と同じである。
	 * &lt;expr&gt;要素のテキスト内で?を見つけたときに、 対応するパラメータ値がjava.util.ListまたはObject[]の
	 * サブクラスだった場合、SQL文字列としてはその?をパラメータ値 の要素数分のカンマリストに置換して（例：要素数が2なら?,?）
	 * その結果をこのメソッドの戻り値として返す。その際、 バインド変数名は、「パラメータ名[要素添え字]」（例： PARAM[0]）という形式になる。
	 * </li>
	 * </ul>
	 * 
	 * @param elem
	 *            &lt;expr&gt;要素
	 * @param params
	 *            パラメータ群
	 * @param varList
	 *            バインド変数リスト
	 * @return SQL文字列
	 * @see #containsAllKeys(java.util.Map, String[])
	 * @see #getAttributeBooleanValue(org.jdom2.Element, String)
	 * @since 1.0.0
	 */
	protected String handleExprElement(Element elem, Map params, List varList) {

		String[] varNames = tokenize(elem.getAttributeValue("var"), " ");
		if (!containsAllKeys(params, varNames)) {
			return null;
		}

		List content = elem.getContent();
		if (content == null) {
			return null;
		}
		boolean repeat = getAttributeBooleanValue(elem, "repeat");
		StringBuffer buf = new StringBuffer();
		int varIndex = 0;
		for (Iterator it = content.iterator(); it.hasNext();) {
			Object part = it.next();
			if (part instanceof Text) {
				String text = ((Text) part).getValue();
				varIndex = handleExprElementPCData(elem, params, text, varNames, repeat, varList, buf, varIndex);
			} else if (part instanceof Element) {
				String text = handleElement((Element) part, params, varList);
				if (text == null) {
					continue;
				}
				buf.append(text);
			}
		}
		return new String(buf);
	}

	/**
	 * &lt;expr&gt;要素のPCDataを処理する。
	 * 
	 * @param elem
	 *            &lt;expr&gt;要素
	 * @param params
	 *            パラメータ集合
	 * @param text
	 *            PCDataテキスト
	 * @param varNames
	 *            変数名配列
	 * @param repeat
	 *            repeat属性が指定されているか
	 * @param varList
	 *            バインド変数リスト
	 * @param buf
	 *            SQL文字列バッファ
	 * @param varIndex
	 *            変数インデクス
	 * @return 変更後の変数インデクス
	 * @since 1.0.0
	 */
	protected int handleExprElementPCData(Element elem, Map params, String text, String[] varNames, boolean repeat,
			List varList, StringBuffer buf, int varIndex) {

		for (int i = 0; i < text.length(); i++) {
			char ch = text.charAt(i);
			if (ch != '?') {
				buf.append(ch);
				continue;
			}
			String name = varNames[varIndex++];
			Object value = getParamValue(params, name);
			if (repeat && (value instanceof List || value instanceof Object[])) {
				List values = null;
				if (value instanceof List) {
					values = (List) value;
				} else {
					values = Arrays.asList((Object[]) value);
				}

				for (int j = 0; j < values.size(); j++) {
					Object elemValue = values.get(j);

					BindVariable var = new BindVariable();
					var.setName(name + "[" + j + "]");
					var.setValue(elemValue);
					if (varTypeMap != null) {
						var.setJdbcType(getVarType(name));
					}
					varList.add(var);

					if (j > 0) {
						buf.append(',');
					}
					buf.append('?');
				}
			} else {
				BindVariable var = new BindVariable();
				var.setName(name);
				var.setValue(value);
				if (varTypeMap != null) {
					var.setJdbcType(getVarType(name));
				}
				varList.add(var);

				buf.append('?');
			}
		}
		return varIndex;
	}

	/**
	 * &lt;expr-list&gt;要素を処理する。
	 * <p>
	 * &lt;expr-list&gt;要素には必ずjoint属性を指定すること。
	 * 未指定の場合はIllegalArgumentExceptionが投げられます。
	 * </p>
	 * <p>
	 * &lt;expr-list&gt;要素ではサブ要素に対して順次handleElement()
	 * を実行して、それぞれの結果をjoint属性値で繋いだものを結果の SQL文字列として返す。ただし、サブ要素の全ての処理結果が
	 * 空文字列だった場合は、このメソッドの戻り値も空文字列を返す。
	 * </p>
	 * <p>
	 * 一方でバインド変数を発見するごとにバインド変数リストに追加していく。
	 * </p>
	 * 
	 * @param elem
	 *            &lt;expr-list&gt;要素
	 * @param params
	 *            パラメータ群
	 * @param varList
	 *            バインド変数リスト
	 * @return SQL文字列
	 * @exception IllegalArgumentException
	 *                XMLまたはパラメータが不正な場合
	 * @see #handleElement(org.jdom2.Element, java.util.Map, java.util.List)
	 * @see #getAttributeBooleanValue(org.jdom2.Element, String)
	 * @since 1.0.0
	 */
	protected String handleExprListElement(Element elem, Map params, List varList) {

		String joint = elem.getAttributeValue("joint");

		List children = elem.getChildren();
		if (children == null) {
			return null;
		}
		StringBuffer buf = new StringBuffer();
		String curJoint = null;
		for (Iterator it = children.iterator(); it.hasNext();) {
			Element child = (Element) it.next();
			String text = handleElement(child, params, varList);
			if (text == null || text.trim().length() == 0) {
				continue;
			}

			if (curJoint != null) {
				buf.append(curJoint);
			}
			buf.append(text);

			// 2つめ以降の空でない要素の前に入れるのは"joint"属性に指定した値
			curJoint = joint;
		}
		String ret = new String(buf);
		if (ret.length() == 0) {
			return null;
		} else {
			return ret;
		}
	}

	/**
	 * &lt;value&gt;要素を処理する。
	 * <p>
	 * &lt;value&gt;要素のvar属性値にはパラメータ名を一つだけ指定する。
	 * 無指定の場合はIllegalArgumentExceptionが投げられる。
	 * また、&lt;value&gt;要素は空要素でなければならない。そうでない場合 はIllegalArgumentExceptionが投げられる。
	 * </p>
	 * <p>
	 * このメソッドでは、引数で指定されたパラメータ群からvar属性で指定された 名前のパラメータの値を取得し、その値を文字列に変換したものを戻り値として
	 * 返す。指定した名前のパラメータが存在しない場合はnullを返す。
	 * </p>
	 * 
	 * @param elem
	 *            &lt;value&gt;要素
	 * @param params
	 *            パラメータ群
	 * @param varList
	 *            バインド変数リスト
	 * @return SQL文字列
	 * @since 1.0.0
	 */
	protected String handleValueElement(Element elem, Map params, List varList) {

		String var = elem.getAttributeValue("var");
		return getParamStringValue(params, var);
	}

	/**
	 * &lt;switch&gt;要素を処理する。
	 * <p>
	 * &lt;switch&gt;要素のvar属性にはパラメータ名を一つだけ指定する。
	 * 無指定の場合はIllegalArgumentExceptionが投げられる。
	 * </p>
	 * <p>
	 * また、&lt;switch&gt;要素のサブ要素としては、複数の&lt;case&gt;要素と
	 * 0または1個の&lt;default&gt;要素を含める。 このメソッドでは、引数で指定されたパラメータ群からvar属性で指定された
	 * 名前のパラメータの値を取得し、&lt;case&gt;要素のvalue属性値と順番に 比較していく。
	 * </p>
	 * <ul>
	 * <li>
	 * 値が一致した場合はその&lt;case&gt;要素に対してhandleCaseElement() を呼び出して戻り値をそのまま返す。</li>
	 * <li>
	 * パラメータ値がどの&lt;case&gt;要素のvalue属性値にも一致しなかった場合
	 * は、&lt;default&gt;要素に対してhandleDefaultElementを呼び出して 戻り値をそのまま返す。</li>
	 * </ul>
	 * 全部不一致の場合に、&lt;default&gt;要素が存在しない場合は
	 * IllegalArgumentExceptionが投げられる。逆にどれかに一致する場合は
	 * &lt;default&gt;要素は存在しなくても構わない。
	 * <p>
	 * なお、パラメータ値が存在しないかnullだった場合は、&lt;case&gt;要素 の参照を一切行わず、全部不一致として扱われる。
	 * </p>
	 * 
	 * @param elem
	 *            &lt;switch&gt;要素
	 * @param params
	 *            パラメータ群
	 * @param varList
	 *            バインド変数リスト
	 * @return SQL文字列
	 * @exception IllegalArgumentException
	 *                XMLまたはパラメータが不正な場合
	 * @see #handleCaseElement(org.jdom2.Element, java.util.Map, java.util.List)
	 * @see #handleDefaultElement(org.jdom2.Element, java.util.Map,
	 *      java.util.List)
	 * @since 1.0.0
	 */
	protected String handleSwitchElement(Element elem, Map params, List varList) {

		String var = elem.getAttributeValue("var");
		String varValue = getParamStringValue(params, var);
		if (varValue != null) {
			List caseElems = elem.getChildren("case");
			for (Iterator it = caseElems.iterator(); it.hasNext();) {
				Element caseElem = (Element) it.next();
				String value = caseElem.getAttributeValue("value");
				if (value != null && value.equals(varValue)) {
					return handleCaseElement(caseElem, params, varList);
				}
			}
		}
		Element defaultElem = elem.getChild("default");
		if (defaultElem == null) {
			throw new IllegalArgumentException(ERRMSG002);
		}
		return handleDefaultElement(defaultElem, params, varList);
	}

	/**
	 * &lt;case&gt;要素を処理する。
	 * <p>
	 * このメソッドは、handleSwitchElement()内の処理でパラメータ名が一致した &lt;case&gt;要素についてのみ呼び出される。
	 * </p>
	 * <p>
	 * 要素のコンテントに対してhandleContent()を呼び、戻り値をそのまま返す。
	 * </p>
	 * 
	 * @param elem
	 *            &lt;case&gt;要素
	 * @param params
	 *            パラメータ群
	 * @param varList
	 *            バインド変数リスト
	 * @return SQL文字列
	 * @see #handleContent(java.util.List, java.util.Map, java.util.List)
	 * @since 1.0.0
	 */
	protected String handleCaseElement(Element elem, Map params, List varList) {

		return handleContent(elem.getContent(), params, varList);
	}

	/**
	 * &lt;default&gt;要素を処理する。
	 * <p>
	 * このメソッドは、handleSwitchElement()内の処理で&lt;default&gt;要素の
	 * 値を参照しようとしたときのみ呼び出される。
	 * </p>
	 * <p>
	 * 要素のコンテントに対してhandleContent()を呼び、戻り値をそのまま返す。
	 * </p>
	 * 
	 * @param elem
	 *            &lt;default&gt;要素
	 * @param params
	 *            パラメータ群
	 * @param varList
	 *            バインド変数リスト
	 * @return SQL文字列
	 * @see #handleContent(java.util.List, java.util.Map, java.util.List)
	 * @since 1.0.0
	 */
	protected String handleDefaultElement(Element elem, Map params, List varList) {

		return handleContent(elem.getContent(), params, varList);
	}

	/**
	 * &lt;call-text&gt;要素を処理する。 要素のテキストをそのまま結果のSQL文字列として返す。
	 * 
	 * @param elem
	 *            &lt;call-text&gt;要素
	 * @param params
	 *            パラメータ群
	 * @param varList
	 *            バインド変数リスト
	 * @return SQL文字列
	 * @since 1.0.0
	 */
	protected String handleCallTextElement(Element elem, Map params, List varList) {

		return elem.getText();
	}

	/**
	 * &lt;parm-list&gt;要素を処理する。
	 * <p>
	 * &lt;parm-list&gt;要素では&lt;param&gt;サブ要素に対して順次
	 * handleParamElement()を実行して、バインド変数を発見するごとに バインド変数リストに追加していく。戻り値としては常にnull
	 * を返す。
	 * </p>
	 * 
	 * @param elem
	 *            &lt;parm-list&gt;要素
	 * @param params
	 *            パラメータ群
	 * @param varList
	 *            バインド変数リスト
	 * @return SQL文字列（常にnull）
	 * @exception IllegalArgumentException
	 *                XMLまたはパラメータが不正な場合
	 * @see #handleParamElement(org.jdom2.Element, java.util.Map, java.util.List)
	 * @since 1.0.0
	 */
	protected String handleParamListElement(Element elem, Map params, List varList) {

		List paramElems = elem.getChildren("param");
		for (Iterator it = paramElems.iterator(); it.hasNext();) {
			Element paramElem = (Element) it.next();
			handleParamElement(paramElem, params, varList);
		}
		return null;
	}

	/**
	 * &lt;param&gt;要素を処理する。
	 * <p>
	 * &lt;param&gt;要素は、ストアドプロシージャ呼び出しの パラメータ定義を行うためのものである。
	 * </p>
	 * <p>
	 * &lt;param&gt;要素にはname属性を必ず指定する。未指定の場合は IllegalArgumentExceptionが投げられる。
	 * また、&lt;param&gt;要素は空要素でなければならない。そうでない場合 はIllegalArgumentExceptionが投げられる。
	 * </p>
	 * <p>
	 * &lt;param&gt;要素につき、バインド変数が1つ作られ、引数の バインド変数リストに追加される。その際、&lt;param&gt;要素
	 * のname属性値がパラメータ名になり、引数のパラメータ群内で そのパラメータ名に対応する値がパラメータ値になる。
	 * </p>
	 * <p>
	 * &lt;param&gt;要素のmode属性値にストアドプロシージャ引数の 入出力モードを指定可能です。指定可能な値は、"in", "out",
	 * "in_out" のいずれかです。mode属性を省略すると"in"として扱われまる。
	 * </p>
	 * <p>
	 * &lt;param&gt;要素のjdbc-type属性値にストアドプロシージャの 出力引数または入出力引数のJDBCタイプを設定可能である。
	 * 指定可能な値は、java.sql.Typesの定数名をそのまま書いたもの
	 * （例："VARCHAR"）である。一覧はTypesUtilクラスのjavadocコメント を参照されたい。
	 * </p>
	 * <p>
	 * &lt;param&gt;要素のscale属性値にストアドプロシージャの 出力引数または入出力引数で数値型のもののスケールを設定可能
	 * です。設定可能な値は整数値を文字列で記述したものである （Integer.parseInt()でパースされる）。
	 * </p>
	 * <p>
	 * &lt;param&gt;要素のscale属性値にストアドプロシージャの 出力引数または入出力引数でREF型のもののユーザタイプ名を
	 * 設定可能である。
	 * </p>
	 * 
	 * @param elem
	 *            &lt;parm&gt;要素
	 * @param params
	 *            パラメータ群
	 * @param varList
	 *            バインド変数リスト
	 * @return SQL文字列（常にnull）
	 * @see TypesUtils TypesUtil
	 * @exception IllegalArgumentException
	 *                XMLまたはパラメータが不正な場合
	 * @since 1.0.0
	 */
	protected String handleParamElement(Element elem, Map params, List varList) {

		String name = elem.getAttributeValue("name");
		String mode = elem.getAttributeValue("mode");
		String jdbcType = elem.getAttributeValue("jdbc-type");
		String scale = elem.getAttributeValue("scale");
		String userType = elem.getAttributeValue("user-type");

		BindVariable bindVar = new BindVariable();
		bindVar.setName(name);
		if (mode == null || "IN".equals(mode)) {
			bindVar.setMode(BindVariable.IN);
		} else if ("OUT".equals(mode)) {
			bindVar.setMode(BindVariable.OUT);
		} else if ("IN_OUT".equals(mode)) {
			bindVar.setMode(BindVariable.IN_OUT);
		}
		if (jdbcType != null) {
			int jdbcTypeIntVal = TypesUtils.getValue(jdbcType);
			bindVar.setJdbcType(jdbcTypeIntVal);
		}
		if (scale != null) {
			int scaleIntVal = Integer.parseInt(scale);
			bindVar.setScale(scaleIntVal);
		}
		if (userType != null) {
			bindVar.setUserTypeName(userType);
		}
		bindVar.setValue(params.get(name));
		varList.add(bindVar);
		return null;
	}

	/**
	 * 要素が空かどうかを返す。
	 *
	 * @param elem 要素
     * @return 要素が空かどうか
     * @since 1.0.0
	 */
	protected boolean isEmptyElement(Element elem) {

		List content = elem.getContent();
		return elem.getText().length() == 0 && (content == null || content.isEmpty());
	}

	/**
	 * 文字列を区切り文字でトークン分割する。
	 * 
	 * @param str
	 *            文字列
	 * @param delim
	 *            区切り文字
	 * @return 分割されたトークンの配列
	 * @see java.util.StringTokenizer java.util.StringTokenizer
	 * @since 1.0.0
	 */
	protected String[] tokenize(String str, String delim) {

		if (str == null) {
			return null;
		}
		StringTokenizer st = new StringTokenizer(str, delim);
		String[] names = new String[st.countTokens()];
		int i = 0;
		while (st.hasMoreTokens()) {
			String name = st.nextToken();
			names[i++] = name;
		}
		return names;
	}

	/**
	 * パラメータマップから指定した変数名に対応する変数値を文字列として取得する。 指定した変数名が存在しない場合はnullを返す。
	 * 
	 * @param params
	 *            パラメータマップ
	 * @param varName
	 *            変数名
	 * @return 変数値
	 * @since 1.0.0
	 */
	protected String getParamStringValue(Map params, String varName) {

		if (params == null) {
			return null;
		}
		Object value = params.get(varName);
		return value == null ? null : value.toString();
	}

	/**
	 * パラメータマップから指定した変数名に対応する変数値を取得する。 指定した変数名が存在しない場合はnullを返す。
	 * 
	 * @param params
	 *            パラメータマップ
	 * @param varName
	 *            変数名
	 * @return 変数値
	 * @since 1.0.0
	 */
	protected Object getParamValue(Map params, String varName) {

		if (params == null) {
			return null;
		}
		Object value = params.get(varName);
		return value == null ? null : value;
	}

	/**
	 * 指定したマップに指定したキー配列の全てのキーが含まれ且つ値がnullでないかを返す。
	 * キー配列がnullの場合はtrueを返し、マップがnullの場合はfalseを返す。
	 * 
	 * @param params
	 *            マップ
	 * @param names
	 *            キー配列
	 * @return 指定したマップに指定したキー配列の全てのキーが含まれ且つ値がnullでないか
	 * @since 1.0.0
	 */
	protected boolean containsAllNonNullValueKeys(Map params, String[] names) {

		if (names == null) {
			return true;
		}
		if (params == null) {
			return false;
		}
		for (int i = 0; i < names.length; i++) {
			String name = names[i];
			if (params.get(name) == null) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 指定したマップに指定したキー配列の全てのキーが含まれるかを返す。
	 * キー配列がnullの場合はtrueを返し、マップがnullの場合はfalseを返す。
	 * 
	 * @param params
	 *            マップ
	 * @param names
	 *            キー配列
	 * @return 指定したマップに指定したキー配列の全てのキーが含まれるか
	 * @since 1.0.0
	 */
	protected boolean containsAllKeys(Map params, String[] names) {

		if (names == null) {
			return true;
		}
		if (params == null) {
			return false;
		}
		for (int i = 0; i < names.length; i++) {
			String name = names[i];
			if (!params.containsKey(name)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 指定した文字列中に指定した文字が何個含まれるかを返す。
	 * 
	 * @param text
	 *            文字列
	 * @param ch
	 *            文字
	 * @return 出現回数
	 * @since 1.0.0
	 */
	protected int countChar(String text, char ch) {

		int count = 0;
		int i = 0;
		while (true) {
			i = text.indexOf(ch, i);
			if (i == -1) {
				break;
			}

			count++;
			if (i + 1 >= text.length()) {
				break;
			}
			i++;
		}
		return count;
	}

	/**
	 * 要素に指定した名前の属性の値が"true"に指定してあるかどうかを返す。 属性値が大文字小文字無視で"true"に等しければtrueを返す。
	 * なお、無指定ならfalseを返す。
	 *
	 * @param elem
	 *            要素
	 * @param attrName
	 *            指定した名前の属性
	 * @return 要素に指定した名前の属性の値が"true"に指定してあるかどうか。
	 * @since 1.0.0
	 */
	protected boolean getAttributeBooleanValue(Element elem, String attrName) {

		return Boolean.valueOf(elem.getAttributeValue(attrName)).booleanValue();
	}

	/**
	 * エラーメッセージ001 (= "Element not found.")
	 */
	public static final String ERRMSG001 = "Element not found.";

	/**
	 * エラーメッセージ002 (=
	 * "Element &lt;default&gt; must exist when parameter value does not match any &lt;case&gt; elements."
	 * )
	 */
	public static final String ERRMSG002 = "Element <default> must exist when parameter value does not match any <case> elements.";
}
