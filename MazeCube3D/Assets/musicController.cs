using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class musicController : MonoBehaviour
   
{
    public GameObject music;
    // Start is called before the first frame update
    void Start()
    {
        if (PlayerPrefs.GetInt("music") == 0) music.GetComponent<AudioSource>().mute = true;
        else music.GetComponent<AudioSource>().mute = false;
    }

    // Update is called once per frame
    void Update()
    {
        if (PlayerPrefs.GetInt("music") == 0) music.GetComponent<AudioSource>().mute = true;
        else music.GetComponent<AudioSource>().mute = false;
    }
}
