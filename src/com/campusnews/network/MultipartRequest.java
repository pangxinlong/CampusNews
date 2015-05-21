package com.campusnews.network;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;

import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.protocol.HTTP;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;
import ch.boye.httpclientandroidlib.HttpEntity;
import ch.boye.httpclientandroidlib.entity.ContentType;
import ch.boye.httpclientandroidlib.entity.mime.HttpMultipartMode;
import ch.boye.httpclientandroidlib.entity.mime.MultipartEntityBuilder;
import ch.boye.httpclientandroidlib.entity.mime.content.FileBody;

import com.alibaba.fastjson.annotation.JSONField;
import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;

public class MultipartRequest extends Request<JSONObject> {

	// private MultipartEntity entity = new MultipartEntity();
 
  
	MultipartEntityBuilder entity = MultipartEntityBuilder.create();
	HttpEntity httpentity;
	private String FILE_PART_NAME = "file";

	private final Response.Listener<JSONObject> mListener;
	private final File mFilePart;
	private final Map<String, String> mStringPart;

	public MultipartRequest(String url, Response.ErrorListener errorListener,
			Response.Listener<JSONObject> listener, File file,
			String uploadName, Map<String, String> mStringPart) {
		super(Method.POST, url, errorListener);
		
		FILE_PART_NAME = uploadName;
		mListener = listener;
		mFilePart = file;
		this.mStringPart = mStringPart;

		Charset charset = Charset.forName("UTF-8");
		entity.setMode(HttpMultipartMode.BROWSER_COMPATIBLE)
				.setCharset(charset);
		ContentType contentType = ContentType.create(HTTP.PLAIN_TEXT_TYPE, HTTP.UTF_8);
		buildMultipartEntity(contentType);
	}

	public void addStringBody(String param, String value) {
		mStringPart.put(param, value);
	}

	private void buildMultipartEntity(ContentType contentType) {
		entity.addPart(FILE_PART_NAME, new FileBody(mFilePart));
		for (Map.Entry<String, String> entry : mStringPart.entrySet()) {
			entity.addTextBody(entry.getKey(),entry.getValue(),contentType);
		}
	}

	@Override
	public String getBodyContentType() {
		return httpentity.getContentType().getValue();
	}

	@Override
	public byte[] getBody() throws AuthFailureError {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		try {
			httpentity = entity.build();
			httpentity.writeTo(bos);
		} catch (IOException e) {
			VolleyLog.e("IOException writing to ByteArrayOutputStream");
		}
		return bos.toByteArray();
	}

	@Override
	protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
		try {
			String jsonString = new String(response.data,
					HttpHeaderParser.parseCharset(response.headers));
			return Response.success(new JSONObject(jsonString),
					HttpHeaderParser.parseCacheHeaders(response));
		} catch (UnsupportedEncodingException e) {
			return Response.error(new ParseError(e));
		} catch (JSONException je) {
			return Response.error(new ParseError(je));
		}
	}

	@Override
	protected void deliverResponse(JSONObject response) {
		mListener.onResponse(response);
	}

}
