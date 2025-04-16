import { Suspense } from "react";
import "./App.css";

import { Product } from "remoteApp/Product";

function App() {
  return (
    <>
      <Suspense fallback={<div>Loading remote component...</div>}>
        <Product />
      </Suspense>
    </>
  );
}

export default App;
