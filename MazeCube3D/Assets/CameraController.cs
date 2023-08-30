using UnityEngine;
using System.Collections.Generic;
using System.Collections;
public class CameraController : MonoBehaviour
{
    //public GameObject player;
    //private Vector3 offset;
    //private Vector3 rotation = new Vector3(1,0,0);
    //void Start()
    //{
    //    offset = transform.position;
    //}
    //void Update()
    //{
    //}
    //void LateUpdate()
    //{
    //    transform.position = player.transform.position + offset;
    //}
    // //Use this for initialization 
    public Transform cameraTarget;
    public float sSpeed = 10.0f;
    public Vector3 dist;
    public Transform lookTarget;
 
    void FixedUpdate() {
        Vector3 dPos = cameraTarget.position + dist;
        Vector3 sPos = Vector3.Lerp(transform.position, dPos, sSpeed * Time.deltaTime);
        transform.position = sPos;
        transform.LookAt(lookTarget.position);
    }
     
}