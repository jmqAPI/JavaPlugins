package com.skywing.utils.picture;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import java.sql.*;

import com.sun.image.codec.jpeg.*;
import java.awt.image.BufferedImage;
import com.skywing.utils.db.*;

public class photoServlet extends HttpServlet {
	
	

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		String operation_id = request.getParameter("operation_id");
		ServletOutputStream output = response.getOutputStream();
		try {
			conn = DbconnManager.getInstance().getConnection();
			stmt = conn.createStatement();

			/*数据库中有一个表包含name(保存的jpg格式文件名)photo(二进制数据)两个字段*/
			String sql = "select * from t_design_picture where operation_id='"
					+ operation_id + "'";
			rs = stmt.executeQuery(sql);
			if (rs.next()) {
				InputStream ins = rs.getBinaryStream("photo");
				JPEGImageDecoder decoder = JPEGCodec.createJPEGDecoder(ins);
				BufferedImage image = decoder.decodeAsBufferedImage();
				JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(output);
				encoder.encode(image);
				image.flush();
				ins.close();
			}
			output.close();
		} catch (Exception cnf) {
			System.out.println("ClassNotFountException:" + cnf);
			output.close();

		}finally{
			DbconnManager.closeResource(conn,stmt,rs);
		}

	}

	public void destroy() {
		
	}
}
