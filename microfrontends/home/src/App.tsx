import { lazy, Suspense } from "react";
import "./App.css";
import { QueryClient, QueryClientProvider } from "@tanstack/react-query";

// const Product = lazy(() =>
//   import("productRemote/Product").then((mod) => ({
//     default: mod.default?.Product || mod.Product, // Try both
//   }))
// );
const Search = lazy(() =>
  import("searchRemote/Search").then((mod) => ({
    default: mod.default?.Search || mod.Search, // Try both
  }))
);

const queryClient = new QueryClient();
function App() {
  return (
    <>
      <QueryClientProvider client={queryClient}>
        {/* <Suspense fallback={<div>Loading remote component...</div>}>
          <Product />
        </Suspense> */}
        <Suspense fallback={<div>Loading remote component...</div>}>
          <Search />
        </Suspense>
      </QueryClientProvider>
    </>
  );
}

export default App;
