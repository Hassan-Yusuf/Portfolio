using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.AI;

public class EnemyNavMesh : MonoBehaviour
{
    NavMeshAgent navMeshAgent;
    public Transform movePositionTransform;
    public bool moveAgent;
    public int speed;
        
    private void Awake()
    {
        navMeshAgent = GetComponent<NavMeshAgent>();
        if (PlayerPrefs.GetInt("difficulty") == 0) GetComponent<NavMeshAgent>().speed = 2;
        else GetComponent<NavMeshAgent>().speed = 3;
    }
    private void Update()
    {
        if (moveAgent) navMeshAgent.destination = movePositionTransform.position;
    }
}
