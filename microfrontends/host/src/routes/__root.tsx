import { Outlet, createRootRouteWithContext } from '@tanstack/react-router'
import { TanStackRouterDevtools } from '@tanstack/react-router-devtools'

import TanStackQueryLayout from './../integrations/tanstack-query/layout.tsx'

import { QueryClient, QueryClientProvider } from '@tanstack/react-query'

interface MyRouterContext {
  queryClient: QueryClient
}
const queryClient = new QueryClient()

export const Route = createRootRouteWithContext<MyRouterContext>()({
  component: () => (
    <>
      <QueryClientProvider client={queryClient}>
        <Outlet />
        <TanStackRouterDevtools />

        <TanStackQueryLayout />
      </QueryClientProvider>
    </>
  ),
})
