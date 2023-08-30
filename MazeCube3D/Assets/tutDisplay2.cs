using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;

public class tutDisplay2 : MonoBehaviour
{
    public GameController game;
    public Text tutorialText;
    public Rigidbody player;
    public GameObject thisObj;
    public EnemyNavMesh enemyAgent;

    void OnTriggerEnter(Collider other)
    {
        displayTutorial1();
    }
    public IEnumerator displayAndWait()
    {
        player.isKinematic = true;
        tutorialText.text = "Avoid enemy agents!\n\nThey will try and stop you!";
        yield return new WaitForSeconds(5);
        tutorialText.text = "";
        thisObj.SetActive(false);
        player.isKinematic = false;
        enemyAgent.moveAgent = true;
    }
    public void displayTutorial1()
    {
        StartCoroutine(displayAndWait());
    }
}
