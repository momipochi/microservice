import { lazy, Suspense } from "react";
import "./App.css";

const Product = lazy(() =>
  import("productRemote/Product").then((x) => x.Empty)
);
const Search = lazy(() => import("searchRemote/Search").then((x) => x.Empty));

function App() {
  return (
    <>
      <Suspense fallback={<div>Loading remote component...</div>}>
        <Product />
      </Suspense>
      <Suspense fallback={<div>Loading remote component...</div>}>
        <Search />
      </Suspense>
    </>
  );
}

export default App;
