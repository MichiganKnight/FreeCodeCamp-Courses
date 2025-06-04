import { Navigate, Route, Routes } from 'react-router-dom'
import { Toaster } from 'react-hot-toast'

import './App.css'

import Home from './Pages/Home/Home'
import Login from './Pages/Login/Login'
import Signup from './Pages/SignUp/SignUp'
import { useAuthContext } from './Context/AuthContext'

function App() {
  const { authUser } = useAuthContext()

  return (
    <div className='p-4 h-screen flex items-center justify-center'>
      <Routes>
        <Route path='/' element={authUser ? <Home /> : <Navigate to={"/Login"} />} />
        <Route path='/Login' element={authUser ? <Navigate to="/" /> : <Login />} />
        <Route path='/Signup' element={authUser ? <Navigate to="/" /> : <Signup />} />
      </Routes>
      <Toaster />
    </div>
  )
}

export default App
