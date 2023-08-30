using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;
using UnityEngine.SceneManagement;

public class MainMenu : MonoBehaviour
{
    public Toggle musicToggle;
    public Toggle easy;
    public void play()
    {
        SceneManager.LoadScene(1);
    }
    public void quit()
    {
        Application.Quit();
    }
    public void musicChecker()
    {
        if (musicToggle.isOn) {PlayerPrefs.SetInt("music", 1); }
        else PlayerPrefs.SetInt("music", 0);
    }
    public void difficultyChecker()
    {
        if (easy.isOn)  PlayerPrefs.SetInt("difficulty", 0);
        else PlayerPrefs.SetInt("difficulty", 1);
    }

}
