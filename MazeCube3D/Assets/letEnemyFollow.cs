using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;

public class letEnemyFollow: MonoBehaviour
{
    public GameObject thisObj;
    public EnemyNavMesh enemyAgent;

    void OnTriggerEnter(Collider other)
    {
        moveAgent();
    }

    public void moveAgent()
    {
        thisObj.SetActive(false);
        enemyAgent.moveAgent = true;
    }
}
