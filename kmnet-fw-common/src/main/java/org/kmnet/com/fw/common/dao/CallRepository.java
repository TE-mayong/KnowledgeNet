/**
 * Project Name:kmnet-fw-common
 * File Name:CallRepository.java
 * Date:2015/04/16 14:14:36
 * Copyright (C) 2016 KnowledgeNet.
 */
package org.kmnet.com.fw.common.dao;

import java.io.Serializable;
import java.util.Map;

/**
 * CallRepository <br>
 * このインタフェースは、シンプルなPL/SQL操作を提供する。<br>
 * 
 * @author Liu.jun
 * @version 1.0 2015/04/16 新規作成
 * @author Ma.Yong
 * @version 1.1 2015/08/18 変更
 * @author Ma.Yong
 * @version 1.2 2015/09/03 callFunctionの方法追加
 * @author Ma.Yong
 * @version 1.3 2015/09/07 throws SQLExceptionの削除
 * @author Liu.jun
 * @version 1.4 2015/09/07 schemaName、catalogNameパラメータを追加しました。
 */
public interface CallRepository extends Serializable {

   /**
    * Procedureを実行する.
    * <p>
    * 
    * @param schemaName
    *            schema名
    * @param catalogName
    *            catalog名
    * @param procedureName
    *            procedure名
    * @param inParamValue
    *            SQLにバインドするinパラメータ
    * @param inParamType
    *            SQLにバインドするinタイプ(Array型のみがある場合設定)
    * @param outParamType
    *            SQLにバインドするoutタイプ
    * @return result 戻り値
    */
    Map<String, Object> callProcedure(final String schemaName, final String catalogName, final String procedureName,
            final Map<String, Object> inParamValue, final Map<String, Object> inParamType,
            final Map<String, Object> outParamType);

    /**
     * Functionを実行する.
     * <p>
     * 
     * @param schemaName
     *            schema名
     * @param catalogName
     *            catalog名
     * @param functionName
     *            function名
     * @param inParamValue
     *            SQLにバインドするinパラメータ
     * @param inParamType
     *            SQLにバインドするinタイプ(Array型のみがある場合設定)
     * @param outParamType
     *            SQLにバインドするoutタイプ
     * @return result 戻り値
     */
    <T> T callFunction(final String schemaName, final String catalogName, final String functionName,
            final Map<String, Object> inParamValue, final Map<String, Object> inParamType, Class<T> outParamType);

}
