using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;

public class tutorialNavigator : MonoBehaviour
{
    public Text tutorialText;

    void OnCollisionEnter(Collision other)
    {
        StartCoroutine(displayAndWait());
    }
    public IEnumerator displayAndWait()
    {
        tutorialText.text = "This is not the tutorial path.\nPlease go the other way to complete!";
        yield return new WaitForSeconds(2);
        tutorialText.text = "";
    }
}
