import axios from "axios";
import Link from "next/link";
import { signIn, signOut, useSession,getSession } from 'next-auth/client'

const BASE_URL_STUDENT = "http://localhost:8080/student/"

export default function Home() {

  const [ session, loading ] = useSession();

  const logOut= async (e)=>{
    e.preventDefault();

    const token = session.accessToken
    const refresh_token = session.refreshToken;
    console.log('logOut',token);
    console.log('logOut',refresh_token);
    const logOutUrl = `http://localhost:8000/auth/realms/BANBEIS/protocol/openid-connect/logout`


    const params = new URLSearchParams()
    params.append('client_id', 'next-client')
    params.append('refresh_token', refresh_token)

    const config = {
      headers: {
        'Content-Type': 'application/x-www-form-urlencoded'
      }
    }

    await axios.post(logOutUrl, params, config)
        .then((result) => {
          console.log(result);
          signOut({ callbackUrl: `/` })
        })
        .catch((err) => {
          console.log(err);
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

      <h2>Student sign in</h2>
      <br/>
      <button onClick={() => signIn()}>Sign in</button>
    </>

    }
    {session &&

    <>
      Signed in as {session.user.email} {JSON.stringify(session)}<br/>

      <button onClick={logOut}>Sign out</button>

      <button onClick={getStudentData}>Get student data</button>
    </>}



    <Link href="/protected">
      <a>Protected</a>
    </Link>
  </>
}
