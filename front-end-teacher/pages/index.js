import styles from '../styles/Home.module.css'
import axios from "axios";

import {useEffect} from "react";
import { signIn, signOut, useSession,getSession } from 'next-auth/client'
const BASE_URL_STUDENT = "http://localhost:8080/teacher/"


export default function Home() {

    const [ session, loading ] = useSession();

    const config = {
        headers: {
            'Content-Type': 'application/json'
        }
    }
    const fetchdata = async ()=>{
        await axios.get(BASE_URL_STUDENT,config)
            .then(res => {
                console.log(res);
            })
            .catch(error => {
                alert('You are not authorize to view the content')
                console.log("Error occured " + error)
            })
    }

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

            <h2>Teacher sign in</h2>
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



           {/* <button  onClick={getStudentData}>Get teacher data</button>*/}
        </>}



    </>
}
