import {
    RouterProvider,
    createBrowserRouter,
} from 'react-router-dom';
import { QueryClientProvider } from '@tanstack/react-query';
import Home from "./components/Home";

const router = createBrowserRouter([
    {
        path: '/',
        element: <Home />,
    },
]);

function App() {
    return (
        <RouterProvider router={router} />
    );
}

export default App;