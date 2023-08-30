using UnityEngine;
using UnityEngine.UI;
using System.Collections;
using System.Collections.Generic;
using UnityEngine.SceneManagement;
public class PlayerController : MonoBehaviour
{
    public float speed;
    public Transform camera, camTarget, lookTarget;
    public Vector3 offset1 = new Vector3(0, 0, -3);
    public Vector3 offset2 = new Vector3(0, 0, 2);
    public Vector3 oldPos, newPos, resetVec;
    public GameController game;
    public Text tutorialText;

    void Start()
    {
        oldPos = transform.position;
    }

    void Update()
    {
        if (Input.GetKey(KeyCode.LeftArrow))
        {
            transform.RotateAround(transform.position, Vector3.up, -40 * Time.deltaTime);
        }
        if (Input.GetKey(KeyCode.RightArrow))
        {
            transform.RotateAround(transform.position, Vector3.up, 40 * Time.deltaTime);
        }
        if (Input.GetKey(KeyCode.UpArrow))
        {
            Vector3 movement = camera.forward;
            GetComponent<Rigidbody>().AddForce(movement * speed * Time.deltaTime);
            //camTarget.position = transform.position + offset1;
            //lookTarget.position = transform.position + offset2;
            
        }
        if (Input.GetKey(KeyCode.DownArrow))
        {
            Vector3 movement = -camera.forward;
            GetComponent<Rigidbody>().AddForce(movement * speed * Time.deltaTime);
            //camTarget.position = transform.position + offset1;
            //lookTarget.position = transform.position + offset2;

        }
        if (Input.GetKeyDown(KeyCode.Space))
        {
            var resetVec = camera.transform.position - transform.position;
            transform.rotation = Quaternion.LookRotation(resetVec);
        }

        newPos = transform.position - oldPos;
        if (newPos.magnitude > 0)
        {
            camTarget.position += newPos;
            lookTarget.position += newPos;
            oldPos = transform.position;
        }
    }
    void OnTriggerEnter(Collider other)
    {
        if (other.gameObject.tag == "PickUp")
        {
            other.gameObject.SetActive(false);
            game.minusCount();
        }
        if(other.gameObject.tag == "Exit")
        {
            game.advance();
        }
        if (other.gameObject.tag == "TWall")
        {
            Debug.Log("lol");
            StartCoroutine(displayAndWait());
        }
    }
    void OnCollisionEnter(Collision other)
    {
        if (other.gameObject.tag == "Enemy")
        {
            GetComponent<Rigidbody>().isKinematic = true;
            SceneManager.LoadScene(4);
        }
        if (other.gameObject.tag == "Death")
        {
            SceneManager.LoadScene(4);
        }
    }
    public IEnumerator displayAndWait()
    {
        tutorialText.text = "This is not the tutorial path.\nPlease go the other way to complete!";
        yield return new WaitForSeconds(2);
        tutorialText.text = "";
    }

}