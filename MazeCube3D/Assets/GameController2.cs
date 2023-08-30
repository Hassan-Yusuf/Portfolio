//Include namespace system
using System;
using System.Collections;
using UnityEngine;
using UnityEngine.UI;
using UnityEngine.SceneManagement;

public class GameController2: MonoBehaviour
{
    bool paused = false;
    public Text pausedText, scoreText, exitText, tutorialText, wayPoints;
    private int numPickups = 4;
    public GameObject Exit, canvas, music;
    public GameObject animationCamera, mainCamera;
    public Rigidbody playerRB;
    public GameObject Player;
    private LineRenderer lineRenderer;
    bool renderBool, lineRendering = true;
    private float shortestDistance, distance;
    public GameObject[] PickUps;
    private GameObject closest;

    void Start()
    {
        if (PlayerPrefs.GetInt("music") == 0)
        {
            music.GetComponent<AudioSource>().mute = true;
        }
        else
        {
            music.GetComponent<AudioSource>().mute = false;
        }
        StartCoroutine(animationIntoGame());
        if (PlayerPrefs.GetInt("difficulty") == 0) renderBool = false;
        else renderBool = true;
        renderBool = true;
        if(renderBool == true) lineRenderer = gameObject.AddComponent<LineRenderer>();

    }
    void startWorking(bool option)
    {
        animationCamera.SetActive(!option);
        mainCamera.SetActive(option);
        canvas.SetActive(option);
        playerRB.isKinematic=!option;

    }

    public IEnumerator animationIntoGame()
    {
        startWorking(false);
        yield return new WaitForSeconds(11);
        startWorking(true);
    }

        void Update()
    {

        if (Input.GetKeyDown("p") && paused == true) { paused = false; }
        else if (Input.GetKeyDown("p")) { paused = true; }

        if (paused == false)
        {
            pausedText.text = "";
            Time.timeScale = 1;
        }
        if (paused == true)
        {
            pausedText.text = "[Game is paused]";
            Time.timeScale = 0;
        }
        if (renderBool == true && numPickups!=0)
        {
            if (lineRendering == true)
            {
                wayPoints.text = "[Waypoints are enabled]\nPress ENTER to disable";
                lineRenderer.enabled = true;
                closestPickUp();
                if (Input.GetKeyDown(KeyCode.Return))
                {
                    lineRendering = false;
                }
            }
            else if (lineRendering == false)
            {
                wayPoints.text = "";
                lineRenderer.enabled = false;
                if (Input.GetKeyDown(KeyCode.Return))
                {
                    lineRendering = true;
                }
            }
        }
        else wayPoints.text = "";
    }
    public GameObject closestPickUp()
    {
        shortestDistance = 10000000;
        for (int x = 0; x <= PickUps.Length - 1; x++)
        {
            if (PickUps[x].activeSelf)
            {
                distance = (Player.transform.position - PickUps[x].transform.position).magnitude;
                if (distance < shortestDistance)
                {
                    shortestDistance = distance;
                    if (closest != null) { closest.GetComponent<Renderer>().material.color = Color.white; }
                    closest = PickUps[x];
                    PickUps[x].GetComponent<Renderer>().material.color = Color.blue;

                }
            }
        }
        if (closest != null)
        {
            lineRenderer.SetPosition(0, Player.transform.position);
            lineRenderer.SetPosition(1, closest.transform.position);
            lineRenderer.SetWidth(0.1f, 0.1f);
        }
        return closest;
    }
    public void minusCount()
    {
        numPickups--;
        SetCountText();
    }
    private void SetCountText()
    {
        scoreText.text = "Number of puzzles left: " + numPickups.ToString();
        if (numPickups==0)
        {
            scoreText.text = "";
            exitText.text = "Find the exit to complete the level!";
            Exit.SetActive(true);
        }
    }
    public void advance()
    {
        SceneManager.LoadScene(SceneManager.GetActiveScene().buildIndex + 1);
    }


}