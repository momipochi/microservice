import { lazy, Suspense } from "react";
import "./App.css";

const Product = lazy(() =>
  import("productRemote/Product").then((mod) => ({
    default: mod.default?.Product || mod.Product, // Try both
  }))
);
const Search = lazy(() =>
  import("searchRemote/Search").then((mod) => ({
    default: mod.default?.Search || mod.Search, // Try both
  }))
);

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
