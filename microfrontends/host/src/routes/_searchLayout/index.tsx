// routes/index.tsx
import { createFileRoute } from '@tanstack/react-router'

export const Route = createFileRoute('/_searchLayout/')({
  component: Home,
})

function Home() {
  return <div>Welcome to the home page</div>
}
