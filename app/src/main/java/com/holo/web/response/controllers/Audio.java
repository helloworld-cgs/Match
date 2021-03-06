package com.holo.web.response.controllers;

import com.holo.web.request.RequestHeader;
import com.holo.web.response.core.MediaController;
import com.holo.web.response.core.session.HttpSession;
import com.holo.web.tools.AndroidAPI;
import com.holo.web.tools.data_set.MediaInfo;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.OutputStream;

/**
 * Created by 根深 on 2016/2/21.
 */
public class Audio extends MediaController {
    final String ID = "id";

    public Audio(OutputStream os, RequestHeader header, HttpSession session) {
        super(os, header, session);
    }

    public void indexAction() {
        int login = session.getSessionInt(LOGIN);
        if (login != 1) {
            redirect("index", "login");
            return;
        }

        JSONObject data = new JSONObject();
        try {
            data.put("title", "音乐");
            data.put("musics", AndroidAPI.getMusicList());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        render("audio/index.html", data);
    }

    public void audioMedia() {
        int login = session.getSessionInt(LOGIN);
        if (login != 1) {
            forbidden();
            return;
        }

        long id = getParams().getLong(ID);
        MediaInfo mediainfo = AndroidAPI.getMediaLocation(id, 1);
        if (mediainfo.ilLegal()) { // file not exist
            notFound();
            return;
        }
        pullOut(mediainfo.file);
    }
}
