using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;

public class tutDisplay : MonoBehaviour
{
    public GameController game;
    public Text tutorialText;
    public Rigidbody player;
    public GameObject thisObj;

    void OnTriggerEnter(Collider other)
    {
        displayTutorial1();
    }
    public IEnumerator displayAndWait()
    {
        player.isKinematic = true;
        tutorialText.text = "Collect puzzles by colliding into them!\n\nYou must collect all puzzles before the exit appears!";
        yield return new WaitForSeconds(5);
        tutorialText.text = "";
        thisObj.SetActive(false);
        player.isKinematic = false;
        yield return new WaitForSeconds(5);
        tutorialText.text = "Tip: If you get stuck press spacebar!";
        yield return new WaitForSeconds(2);
        tutorialText.text = "";
    }
    public void displayTutorial1()
    {
        StartCoroutine(displayAndWait());
    }
}
