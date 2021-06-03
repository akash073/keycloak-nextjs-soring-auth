import axios from "axios";
import Link from "next/link";
import { signIn, signOut, useSession,getSession } from 'next-auth/client'
import getConfig from 'next/config'
import {useEffect} from "react";
// Only holds serverRuntimeConfig and publicRuntimeConfig
const { serverRuntimeConfig, publicRuntimeConfig } = getConfig()

const BASE_URL_STUDENT = "http://localhost:8080/"

export default function Home() {

  const [ session, loading ] = useSession();

  useEffect(()=>{
    var data = JSON.stringify(session);
    console.log('Data',data)
  })




  const getStudentData = async ()=>{
    console.log('Cookie created name : user ');

    const data = {name: session?.user.email,token : session.accessToken}
    /*setCookie("user", JSON.stringify(data), {
      path: "/",
      maxAge: 3600, // Expires after 1hr
      sameSite: false,
      domain: "demo-board.com"
    })*/

    const config = {
      headers: {
        'Content-Type': 'application/json',
        'Authorization': 'Bearer ' +  session.accessToken
      }
    }

    await axios.get(BASE_URL_STUDENT,config)
        .then(res => {
          console.log(res);
        })
        .catch(error => {
          alert('You are not authorize to view the content')
          console.log("Error occured " + error)
        }
        )
  }

  return <>


    {!session &&
    <>

      <h2>Student sign in</h2>
      {/*<br/>
      <button onClick={() => signIn()}>Sign in</button>*/}
    </>

    }
    {session &&

    <>
      Signed in as {session.user.email}

      <br/>

      User Name = {session.user.name}

      <br/>



     {/* <button  onClick={getStudentData}>Get student data</button>*/}
    </>}



  </>
}
