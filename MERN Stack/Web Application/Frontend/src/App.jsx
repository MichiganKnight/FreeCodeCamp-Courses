import { Box, useColorModeValue } from "@chakra-ui/react"
import { Route, Routes } from "react-router-dom"
import CreatePage from "./Pages/CreatePage"
import HomePage from "./Pages/HomePage"
import Navbar from "./Components/Navbar"
import { useProductStore } from "./Store/Product"

function App() {
  const { products } = useProductStore()

  return (
    <Box minH={"100vh"} bg={useColorModeValue("gray.100", "gray.900")}>
      <Navbar />

      <Routes>
        <Route path="/" element={<HomePage />} />
        <Route path="/Create" element={<CreatePage />} ></Route>
      </Routes>
    </Box>
  )
}

export default App
