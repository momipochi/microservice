import { lazy, Suspense } from 'react'
import { ShoppingCart, Bell, MessageCircle } from 'lucide-react' // Using lucide for lightweight icons
import { Link } from '@tanstack/react-router'

const Search = lazy(() =>
  import('searchRemote/Search').then((mod) => ({
    default: mod.default?.Search || mod.Search,
  })),
)

export function Header() {
  return (
    <header className="bg-white shadow-md">
      <div className="flex items-center justify-between max-w-[1248px] mx-auto pr-10 pl-10 p-4">
        {/* Left: Logo */}
        <div className="flex items-center gap-4">
          <Link to="/" className="block">
            <img
              className="w-10 h-10 rounded-full cursor-pointer"
              src="https://cdn-icons-png.flaticon.com/512/57/57589.png"
              alt="ecommerce"
            />
          </Link>
        </div>

        {/* Center: Search */}
        <div className="flex-1 mx-4 max-w-xl">
          <Suspense fallback={<div>Loading search...</div>}>
            <Search />
          </Suspense>
        </div>

        {/* Right: Icons */}
        <div className="flex items-center gap-4">
          <button className="text-gray-600 hover:text-black">
            <ShoppingCart size={20} />
          </button>
          <button className="text-gray-600 hover:text-black">
            <Bell size={20} />
          </button>
          <button className="text-gray-600 hover:text-black">
            <MessageCircle size={20} />
          </button>
        </div>
      </div>
    </header>
  )
}
