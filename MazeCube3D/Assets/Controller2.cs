using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class Controller2 : MonoBehaviour
{
    public Transform player;
    public Vector3 offset = new Vector3(0, 1, -3);
    public Vector3 offsetCam; public float speed = 500;
    public Vector3 newPos, oldPos;
    public Quaternion rotation;
    // Start is called before the first frame update
    void Start()
    {
        rotation = transform.rotation;
    }
    // alternate method#2:
    // how far from parent to be
    void FixedUpdate()
    {
        //transform.position = player.position + offset;
        //transform.rotation = Quaternion.Euler(0, player.rotation.eulerAngles.y, 0);
    }
    // Update is called once per frame
    void Update()
    {
        //transform.rotation = transform.rotation - player.rotation;
        //transform.position = Vector3.Lerp(transform.position, new Vector3(player.position.x, player.position.y, player.position.z), speed * Time.deltaTime);
        if (Input.GetKey(KeyCode.LeftArrow))
        {
            transform.RotateAround(player.position, Vector3.up, -40 * Time.deltaTime);
        }
        if (Input.GetKey(KeyCode.RightArrow))
        {
            transform.RotateAround(player.position, Vector3.up, 40 * Time.deltaTime);
        }
        rotation = transform.rotation;
        oldPos = player.position;

    }
    void LateUpdate()
    {
      
        //newPos = player.position - oldPos;
        //transform.position += newPos;

    }
}