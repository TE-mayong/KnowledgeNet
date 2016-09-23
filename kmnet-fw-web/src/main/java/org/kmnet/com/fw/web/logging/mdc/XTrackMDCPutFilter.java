/*
 * Copyright (C) 2016 KnowledgeNet.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
/*
 * Modified by KnowledgeNet Inc.
 * <li>Ver.1.0 2016/02/14 新規作成(Shape-Up)</li>
 */
package org.kmnet.com.fw.web.logging.mdc;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.MDC;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * LogbackのMDCにログ全体を紐づける為のリクエストIDを払いだします
 * 
 */
public class XTrackMDCPutFilter extends OncePerRequestFilter {

	/** リクエストIDのMDC名 */
	public static final String REQUEST_ID = "ReqID";

	/** セッションIDのMDC名 */
	public static final String SESSION_ID = "SesID";

	/** リクエストIDの長さ. */
	private static final int RANDAM_FORMAT_LENGTH = 5;

	/**
	 * MDCに値を設定します.
	 * 
	 * @param request
	 *            リクエスト
	 * @param response
	 *            レスポンス
	 * @param chain
	 *            フィルターチェーン
	 * @throws IOException
	 *             I/O例外発生
	 * @throws ServletException
	 *             例外発生
	 */
	@Override
	protected void doFilterInternal(HttpServletRequest request,
			HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		MDC.put(REQUEST_ID,
				RandomStringUtils.randomAlphanumeric(RANDAM_FORMAT_LENGTH));

		HttpSession session = ((HttpServletRequest) request).getSession(true);
		// sessionidをそのままログ出力するわけにはいかないのでハッシュを取得
		String sessionIdentifier = (Integer.toHexString(session.hashCode()));
		MDC.put(SESSION_ID, sessionIdentifier);

		// ------------------------
		// 後続Filter処理
		// ------------------------
		try {
			filterChain.doFilter(request, response);
		} finally {

			// ------------------------
			// 処理後
			// ------------------------
			MDC.remove(REQUEST_ID);
			MDC.remove(SESSION_ID);
		}

	}

}
