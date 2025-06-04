import { Route, Routes } from 'react-router-dom'
import { Toaster } from 'react-hot-toast'

import './App.css'

import Home from './Pages/Home/Home'
import Login from './Pages/Login/Login'
import Signup from './Pages/SignUp/SignUp'

function App() {
  return (
    <div className='p-4 h-screen flex items-center justify-center'>
      <Routes>
        <Route path='/' element={<Home />} />
        <Route path='/Login' element={<Login />} />
        <Route path='/Signup' element={<Signup />} />
      </Routes>
      <Toaster />
    </div>
  )
}

export default App
