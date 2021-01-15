package com.example.kugoupc


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.kugoupc.databinding.ActivityMainBinding
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import org.json.JSONArray
import org.json.JSONObject
import java.net.URL

class MainActivity : AppCompatActivity() {
    val t: Float = 1.111111111111111E12F
    lateinit var keyword: String
    lateinit var url0: String
    lateinit var s0:String
    var left:Int = 0
    lateinit var list0:JSONObject
    lateinit var list1: JSONArray
    lateinit var a:JSONObject
    lateinit var filehash: String
    lateinit var albumid: String
    lateinit var url1:String
    lateinit var s1:String
    var right:Int=0
    lateinit var date: JSONObject
    lateinit var audio_name:String
    lateinit var album_name:String
    lateinit var play_url:String
    private lateinit var binding:ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.button.setOnClickListener {
            binding.textview.text=""
            keyword = binding.edittext.text.toString()
            doAsync {
                url0 = "https://songsearch.kugou.com/song_search_v2?callback=jQuery112405132987859127838_${t}&page=1&pagesize=30&userid=-1&clientver=&platform=WebFilter&tag=em&filter=2&iscorrection=1&privilege_filter=0&_=${t}&keyword=${keyword}"
                s0 = URL(url0).readText()
                left = s0.indexOf("{")
                right=s0.lastIndexOf("}")
                s0 = s0.substring(left,right+1)
                list0 = JSONObject(s0).getJSONObject("data")
                list1 = list0.getJSONArray("lists")
                for (i in 0 until list1.length()) {
                    a = list1.getJSONObject(i)
                    filehash = a.getString("FileHash")
                    albumid = a.getString("AlbumID")
                    url1 = "https://wwwapi.kugou.com/yy/index.php?r=play/getdata&callback=jQuery19108991040761831859_1607742705511&hash=${filehash}&dfid=334lgQ3gvzHD1503TJ1eRVym&mid=449fc6a9349f6b64fc0d58efab406b8d&platid=4&album_id=${albumid}&_=1607742705512"
                    s1 = URL(url1).readText()
                    right = s1.lastIndexOf("}")
                    left=s1.indexOf("{")
                    s1 = s1.substring(left, right+1)
                    date = JSONObject(s1).getJSONObject("data")
                    audio_name = date.getString("audio_name")
                    album_name = date.getString("album_name")
                    play_url = date.getString("play_url")
                    uiThread {
                    binding.textview.append("\n歌名:${audio_name}\n专辑名:${album_name}\n下载链接:${play_url}\n")
                    }
                }
            }
        }
    }
}
