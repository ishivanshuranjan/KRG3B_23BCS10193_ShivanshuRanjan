import { useState } from "react";
import useForm from "../hooks/useForm";

function TaskManager() {

    const [tasks, setTasks] = useState([]);

    const { values, handleChange, resetForm } = useForm({
        title: "",
        priority: "Low"
    });

    function handleSubmit(e) {
        e.preventDefault();

        if (values.title.trim() === "") {
            alert("Please enter task title");
            return;
        }

        setTasks([...tasks, values]);

        resetForm();
    }

    return (
        <div className="container">

            <h2>Task Manager</h2>

            <form onSubmit={handleSubmit}>

                <input
                    type="text"
                    name="title"
                    placeholder="Task Title"
                    value={values.title}
                    onChange={handleChange}
                />

                <select
                    name="priority"
                    value={values.priority}
                    onChange={handleChange}
                >
                    <option value="Low">Low</option>
                    <option value="Medium">Medium</option>
                    <option value="High">High</option>
                </select>

                <button type="submit">
                    Add Task
                </button>

            </form>

            <h3>Task List</h3>

            <ul>
                {tasks.map((task, index) => (
                    <li key={index}>
                        {task.title} | {task.priority}
                    </li>
                ))}
            </ul>

        </div>
    );
}

export default TaskManager;